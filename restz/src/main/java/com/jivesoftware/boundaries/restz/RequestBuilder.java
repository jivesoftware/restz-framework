package com.jivesoftware.boundaries.restz;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by bmoshe on 4/1/14.
 */
public class RequestBuilder
{
    private String url;
    private HttpVerb httpVerb;

    private MultivaluedMap<String, String> headers;
    private Map<String, String> params;

    private Object entity;

    public RequestBuilder(String url, HttpVerb httpVerb)
    {
        this.url = url;
        this.httpVerb = httpVerb;

        headers = new MultivaluedHashMap<>();
        params = new HashMap<>();
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public HttpVerb getHttpVerb()
    {
        return httpVerb;
    }

    public void setHttpVerb(HttpVerb httpVerb)
    {
        this.httpVerb = httpVerb;
    }

    public Set<String> getHeaderNames()
    {
        return headers.keySet();
    }


    public List<String> getHeader(String name)
    {
        return headers.get(name);
    }

    public RequestBuilder addHeader(String name, String value)
    {
        if(value != null)
            headers.putSingle(name, value);

        return this;
    }

    public RequestBuilder clearHeader(String name)
    {
        headers.remove(name);
        return this;
    }

    public Set<String> getParamNames()
    {
        return params.keySet();
    }

    public String getParam(String name)
    {
        return params.get(name);
    }

    public RequestBuilder setParam(String name, String value)
    {
        params.put(name, value);
        return this;
    }

    public RequestBuilder clearParam(String name)
    {
        params.remove(name);
        return this;
    }

    public Object getEntity()
    {
        return entity;
    }

    public RequestBuilder setEntity(Object entity)
    {
        this.entity = entity;
        return this;
    }
}
