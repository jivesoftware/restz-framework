package com.jivesoftware.boundaries.restz;

import java.io.IOException;

/**
 * Created by bmoshe on 6/24/14.
 */
public class DummyConnectionCloser
implements ConnectionCloser
{
    @Override
    public void close()
    throws IOException
    {
    }
}