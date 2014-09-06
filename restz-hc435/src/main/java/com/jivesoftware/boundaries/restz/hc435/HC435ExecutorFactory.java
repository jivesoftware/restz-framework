package com.jivesoftware.boundaries.restz.hc435;

import com.jivesoftware.boundaries.restz.Executor;
import com.jivesoftware.boundaries.restz.ExecutorFactory;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by bmoshe on 8/31/14.
 */
public class HC435ExecutorFactory
implements ExecutorFactory
{
    private final static Logger log = Logger.getLogger(HC435ExecutorFactory.class.getSimpleName());

    protected final HttpClientConnectionManager httpClientConnectionManager;

    public HC435ExecutorFactory(HttpClientConnectionManager httpClientConnectionManager)
    {
        this.httpClientConnectionManager = httpClientConnectionManager;
    }

    public Executor get()
    {
        final Executor executor = acquireExecutor();
        return executor;
    }

    private Executor acquireExecutor()
    {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        CloseableHttpClient httpClient = httpClientBuilder
            .setConnectionManager(httpClientConnectionManager)
            .setSSLSocketFactory(SSLConnectionSocketFactory.getSocketFactory())
            .build();

        return new HC435Executor(httpClient);
    }

    public void debug()
    {
        if(httpClientConnectionManager instanceof PoolingHttpClientConnectionManager)
            log.log(Level.INFO, ((PoolingHttpClientConnectionManager) httpClientConnectionManager).getTotalStats().toString());

        else
            log.log(Level.WARNING, "Cannot debug " + httpClientConnectionManager.getClass().getSimpleName() + " connection managers");
    }
}
