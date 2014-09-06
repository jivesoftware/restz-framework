package com.jivesoftware.boundaries.restz.layers;

import com.jivesoftware.boundaries.restz.Layer;
import com.jivesoftware.boundaries.restz.RequestBuilder;
import com.jivesoftware.boundaries.restz.exceptions.FailureException;
import com.jivesoftware.boundaries.restz.exceptions.HttpFailureException;

/**
 * Created by bmoshe on 4/8/14.
 */
public interface RecoverableFailureLayer
extends Layer
{
    void hasFailed(RequestBuilder requestBuilder, HttpFailureException exception) throws FailureException;

    boolean recover(RequestBuilder requestBuilder, FailureException exception);
}