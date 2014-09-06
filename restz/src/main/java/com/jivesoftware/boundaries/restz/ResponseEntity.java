package com.jivesoftware.boundaries.restz;

import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

/**
 * Created by bmoshe on 4/9/14.
 */
public class ResponseEntity<T>
{
    private T entity;
    private Status status;

    private MultivaluedMap<String, String> headers;

    public ResponseEntity(T entity, Status status, MultivaluedMap<String, String> headers)
    {
        this.entity = entity;
        this.status = status;

        this.headers = headers;
    }

    public T getEntity()
    {
        return entity;
    }

    public void setEntity(T entity)
    {
        this.entity = entity;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public MultivaluedMap<String, String> getHeaders()
    {
        return headers;
    }

    public void setHeaders(MultivaluedMap<String, String> headers)
    {
        this.headers = headers;
    }
}
