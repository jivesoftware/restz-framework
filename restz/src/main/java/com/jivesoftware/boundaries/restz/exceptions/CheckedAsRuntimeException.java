package com.jivesoftware.boundaries.restz.exceptions;

/**
 * Created by bmoshe on 6/23/14.
 */
public class CheckedAsRuntimeException
extends FailureException
{
    private final Exception e;

    public CheckedAsRuntimeException(String message, Exception e)
    {
        super(message, e);
        this.e = e;
    }

    public Exception getException()
    {
        return e;
    }
}