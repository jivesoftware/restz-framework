package com.jivesoftware.boundaries.restz;

import java.io.IOException;

/**
 * Created by bmoshe on 8/31/14.
 */
public interface Executor
{
    Response execute(RequestBuilder requestBuilder) throws IOException;
}
