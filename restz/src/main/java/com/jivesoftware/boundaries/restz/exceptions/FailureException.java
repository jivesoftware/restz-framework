package com.jivesoftware.boundaries.restz.exceptions;

/**
 * Created by bmoshe on 4/7/14.
 */
public abstract class FailureException
extends RuntimeException
{
    public FailureException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public FailureException(String message)
    {
        super(message);
    }

    public FailureException()
    {
        super();
    }
}
