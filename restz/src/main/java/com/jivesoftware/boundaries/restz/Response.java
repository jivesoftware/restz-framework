package com.jivesoftware.boundaries.restz;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bmoshe on 4/1/14.
 */
public class Response
implements Closeable, ConnectionCloser
{
    private Status status;
    private MultivaluedMap<String, String> headers;

    private InputStream content;
    private ConnectionCloser connectionCloser;

    public Response(Status status, MultivaluedMap<String, String> headers, InputStream content, ConnectionCloser connectionCloser)
    {
        this.status = status;
        this.headers = headers;

        this.content = content;
        this.connectionCloser = connectionCloser;
    }

    public Status getStatus()
    {
        return status;
    }

    public MultivaluedMap<String, String> getHeaders()
    {
        return headers;
    }

    public InputStream getContent()
    {
        return content;
    }

    @Override
    public void close()
    throws IOException
    {
        connectionCloser.close();
    }
}

