package com.jivesoftware.boundaries.restz;

import org.apache.commons.io.input.ProxyInputStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bmoshe on 4/10/14.
 */
public class ConnectionClosingInputStream
extends ProxyInputStream
{
    private final ConnectionCloser connectionCloser;

    public ConnectionClosingInputStream(InputStream proxy, ConnectionCloser connectionCloser)
    {
        super(proxy);

        this.connectionCloser = connectionCloser;
    }

    @Override
    public void close()
    throws IOException
    {
        super.close();
        connectionCloser.close();
    }
}
