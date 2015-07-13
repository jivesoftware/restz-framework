package com.jivesoftware.boundaries.restz.hc450;

import com.jivesoftware.boundaries.restz.ConnectionCloser;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.IOException;

/**
 * Created by bmoshe on 4/10/14.
 */
public class HC450ConnectionCloser
implements ConnectionCloser
{
    private final HttpRequestBase request;

    public HC450ConnectionCloser(HttpRequestBase request)
    {
        this.request = request;
    }

    @Override
    public void close()
    throws IOException
    {
        request.releaseConnection();
    }
}
