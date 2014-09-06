package com.jivesoftware.boundaries.restz.strategies;

import com.jivesoftware.boundaries.restz.*;
import com.jivesoftware.boundaries.restz.exceptions.CheckedAsRuntimeException;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by bmoshe on 4/2/14.
 */
public class SmartReadingStrategy
implements ResponseReadingStrategy
{
    private static final Logger log = Logger.getLogger(SmartReadingStrategy.class.getSimpleName());

    private final ResponseReadingStrategy internalReadingStrategy;

    public SmartReadingStrategy()
    {
        this(ResponseReadingStrategy.gsonResponseReadingStrategy);
    }

    public SmartReadingStrategy(ResponseReadingStrategy internalReadingStrategy)
    {
        this.internalReadingStrategy = internalReadingStrategy;
    }


    public <T> T read(Response response, Class<T> tClass)
    throws IOException
    {
        if (void.class.equals(tClass))
        {
            response.close();
            return null;
        }

        InputStream content = response.getContent();
        if (InputStream.class.equals(tClass))
            return (T) new ConnectionClosingInputStream(content, response);

        if (String.class.equals(tClass))
        {
            String contentAsString = parseAsString(content);

            response.close();
            return (T) contentAsString;
        }

        if (Response.class.equals(tClass))
            return (T) response;

        if (ResponseEntity.class.equals(tClass.getSuperclass()))
        {
            ParameterizedType t = (ParameterizedType) tClass.getGenericSuperclass(); // OtherClass<String>
            Class<?> clazz = (Class<?>) t.getActualTypeArguments()[0];

            Object entity = read(response, clazz);

            Status status = response.getStatus();
            MultivaluedMap<String, String> headers = response.getHeaders();

            return (T) new ResponseEntity<>(entity, status, headers);
        }

        T entity = internalReadingStrategy.read(response, tClass);
        return entity;
    }

    private static String parseAsString(InputStream content)
    {
        try
        (
            InputStreamReader inReader = new InputStreamReader(content);
            BufferedReader reader = new BufferedReader(inReader);
        )
        {

            String rawContent = "";

            String line = reader.readLine();
            while (line != null) {
                rawContent += line;
                line = reader.readLine();
            }

            return rawContent;
        }
        catch(IOException e)
        {
            final String msg = "Failed to parse response as String - " + e.getMessage();

            log.log(Level.WARNING, msg);
            throw new CheckedAsRuntimeException(msg, e);
        }
        finally
        {
            IOUtils.closeQuietly(content);
        }
    }
}
