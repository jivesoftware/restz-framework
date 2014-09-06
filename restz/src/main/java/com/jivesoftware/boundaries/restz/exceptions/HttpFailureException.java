package com.jivesoftware.boundaries.restz.exceptions;

import com.jivesoftware.boundaries.restz.ResponseEntity;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;

/**
 * Created by bmoshe on 4/9/14.
 */
public class HttpFailureException
extends FailureException
{
    private ResponseEntity<String> responseEntity;

    public HttpFailureException(ResponseEntity<String> responseEntity)
    {
        this.responseEntity = responseEntity;
    }

    public Status getStatus()
    {
        return responseEntity.getStatus();
    }

    public MultivaluedMap<String, String> getHeaders()
    {
        return responseEntity.getHeaders();
    }

    public String getContent()
    {
        return responseEntity.getEntity();
    }
}
