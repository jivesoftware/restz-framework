package com.jivesoftware.boundaries.restz.hc400;

import com.google.gson.Gson;
import com.jivesoftware.boundaries.restz.*;
import com.jivesoftware.boundaries.restz.exceptions.CheckedAsRuntimeException;
import com.jivesoftware.boundaries.restz.multipart.*;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by bmoshe on 4/1/14.
 */
public class HC400Executor
implements Executor
{
    private static final Logger log = Logger.getLogger(HC400Executor.class.getSimpleName());

    private final HttpClient httpClient;
    private InputStream lastContent;

    HC400Executor(HttpClient httpClient)
    {
        this.httpClient = httpClient;
        this.lastContent = null;
    }

    @Override
    public Response execute(RequestBuilder requestBuilder)
    throws IOException
    {
        final HttpUriRequest request = build(requestBuilder);
        final Response response = execute(request);

        return response;
    }

    private HttpUriRequest build(RequestBuilder requestBuilder)
    throws IOException
    {
        if(lastContent != null)
            lastContent.close();

        String url = requestBuilder.getUrl();
        HttpVerb httpVerb = requestBuilder.getHttpVerb();

        HttpUriRequest request = null;
        HttpEntity httpEntity = null;

        switch(httpVerb) {
            case GET:
                request = new HttpGet(url);

            break;

            case PUT:
                HttpPut put = new HttpPut(url);

                httpEntity = acquireEntity(requestBuilder);
                put.setEntity(httpEntity);

                request = put;

            break;

            case POST:
                HttpPost post = new HttpPost(url);

                httpEntity = acquireEntity(requestBuilder);
                post.setEntity(httpEntity);

                request = post;

            break;

            case DELETE:
                request = new HttpDelete(url);

            break;

            case OPTIONS:
                request = new HttpOptions(url);

            break;
        }

        addHeaders(requestBuilder, request);
        return request;
    }

    private HttpEntity acquireEntity(RequestBuilder requestBuilder)
    throws UnsupportedEncodingException
    {
        Object entity = requestBuilder.getEntity();
        HttpEntity httpEntity = null;

        if(entity == null)
            httpEntity = acquireFormParams(requestBuilder);

        else
        if(entity instanceof MultipartEntityBuilder)
            httpEntity = buildMultipart((MultipartEntityBuilder) entity);

        else
        if(entity instanceof InputStream)
            httpEntity = new InputStreamEntity((InputStream) entity, -1);

        else
        if(entity instanceof File)
        {
            File entityAsFile = (File) entity;
            String contentType = probeContentTypeByFile(entityAsFile);

            httpEntity = new FileEntity(entityAsFile, contentType);
        }
        else
            httpEntity = serializeAsRequestEntity(entity);

        return httpEntity;
    }

    private HttpEntity buildMultipart(MultipartEntityBuilder entity)
    throws UnsupportedEncodingException
    {
        final MultipartEntity multipartEntity = new MultipartEntity();

        final List<Part> parts = entity.getParts();
        for(Part part : parts)
            if(part instanceof TextPart)
            {
                final TextPart textPart = (TextPart) part;
                multipartEntity.addPart(part.getName(), new StringBody(textPart.getText(), textPart.getContentType(), Charset.defaultCharset()));
            }
            else
            if(part instanceof InputStreamPart)
            {
                final InputStreamPart inPart = (InputStreamPart) part;
                multipartEntity.addPart(inPart.getName(), new InputStreamBody(inPart.getContent(), inPart.getContentType(), inPart.getFileName()));
            }
            else
            if(part instanceof FilePart)
            {
                final FilePart filePart = (FilePart) part;
                multipartEntity.addPart(filePart.getName(), new FileBody(filePart.getContent(), filePart.getContentType()));
            }
            else
            if(part instanceof ByteArrayPart)
            {
                final ByteArrayPart byteArrayPart = (ByteArrayPart) part;
                final byte[] byteArray = byteArrayPart.getContent();

                final ByteArrayInputStream byteArrayAsInputStream = new ByteArrayInputStream(byteArray);
                multipartEntity.addPart(byteArrayPart.getName(), new InputStreamBody(byteArrayAsInputStream, byteArrayPart.getContentType(), byteArrayPart.getFileName()));
            }

        final HttpEntity httpEntity = multipartEntity;
        return httpEntity;
    }

    private String probeContentTypeByFile(File file)
    {
        try
        {
            Path entityFilePath = Paths.get(file.toURI());

            String contentType = Files.probeContentType(entityFilePath);
            return contentType;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "application/octet-stream";
        }
    }

    private UrlEncodedFormEntity acquireFormParams(RequestBuilder requestBuilder)
    {
        final Set<String> paramNames = requestBuilder.getParamNames();
        if(!paramNames.isEmpty())
        {
            try
            {
                List<NameValuePair> formParams = new LinkedList<>();
                for(String name : paramNames)
                {
                    final String value = requestBuilder.getParam(name);
                    formParams.add(new BasicNameValuePair(name, value));
                }

                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams);
                return formEntity;
            }
            catch (UnsupportedEncodingException e)
            {
                log.log(Level.WARNING, "Failed to process form params - " + e.getMessage());
                throw new CheckedAsRuntimeException("Failed to process form params - " + e.getMessage(), e);
            }
        }

        return null;
    }

    private void addHeaders(RequestBuilder requestBuilder, HttpUriRequest request)
    {
        final Set<String> headerNames = requestBuilder.getParamNames();
        for(String name : headerNames)
        {
            List<String> values = requestBuilder.getHeader(name);
            for(String value : values)
                request.addHeader(name, value);
        }
    }

    private Response execute(HttpUriRequest request)
    throws IOException
    {
        HttpResponse response = httpClient.execute(request);

        Status status = extractStatus(response);
        MultivaluedMap<String, String> headers = extractHeaders(response);
        InputStream content = extractContent(response);

        lastContent = content;
        return new Response(status, headers, content, new DummyConnectionCloser());
    }

    private Status extractStatus(HttpResponse response)
    {
        // Get Status Code
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();

        Status status = Status.fromStatusCode(statusCode);
        return status;
    }

    private MultivaluedMap<String, String> extractHeaders(HttpResponse response)
    {
        // Get Header MultivaluedMap
        Header[] rawHeaders = response.getAllHeaders();
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();

        for(Header header : rawHeaders)
        {
            String name = header.getName();
            String value = header.getValue();

            headers.putSingle(name, value);
        }

        return headers;
    }

    private InputStream extractContent(HttpResponse response)
    {
        try
        {
            HttpEntity entity = response.getEntity();
            return entity.getContent();
        }
        catch(IOException e)
        {
            log.log(Level.WARNING, "Failed to extract response content - " + e.getMessage());
            throw new CheckedAsRuntimeException("Failed to extract response content - " + e.getMessage(), e);
        }
    }

    private static HttpEntity serializeAsRequestEntity(Object o)
    {
        try
        {
            Gson gson = new Gson();
            String json = gson.toJson(o);

            return new StringEntity(json);
        }
        catch (UnsupportedEncodingException e)
        {
            log.log(Level.WARNING, "Failed to serialize request entity - " + e.getMessage());
            throw new CheckedAsRuntimeException("Failed to serialize request entity - " + e.getMessage(), e);
        }
    }
}
