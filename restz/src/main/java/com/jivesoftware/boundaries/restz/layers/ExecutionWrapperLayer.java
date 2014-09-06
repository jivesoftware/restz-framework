package com.jivesoftware.boundaries.restz.layers;

import com.jivesoftware.boundaries.restz.*;
import com.jivesoftware.boundaries.restz.exceptions.FailureException;
import com.jivesoftware.boundaries.restz.RequestBuilder;

/**
 * Created by bmoshe on 4/8/14.
 */
public interface ExecutionWrapperLayer
extends Layer
{
    void beforeExecution(RequestBuilder requestBuilder);
    void afterExecution(RequestBuilder requestBuilder, Response response) throws FailureException;
}
