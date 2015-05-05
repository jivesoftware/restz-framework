package com.jivesoftware.boundaries.restz.hc432;

import com.jivesoftware.boundaries.restz.Executor;
import com.jivesoftware.boundaries.restz.ExecutorFactory;
import com.jivesoftware.boundaries.serializing.GsonSerializer;
import com.jivesoftware.boundaries.serializing.Serializer;
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
public class HC432ExecutorFactory
implements ExecutorFactory
{
    private final static Logger log = Logger.getLogger(HC432ExecutorFactory.class.getSimpleName());

    protected final HttpClientConnectionManager httpClientConnectionManager;
    protected final Serializer serializer;

    public HC432ExecutorFactory(HttpClientConnectionManager httpClientConnectionManager)
    {
        this(httpClientConnectionManager, new GsonSerializer());
    }

    public HC432ExecutorFactory(HttpClientConnectionManager httpClientConnectionManager, Serializer serializer)
    {
        this.httpClientConnectionManager = httpClientConnectionManager;
        this.serializer = serializer;
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

        return new HC432Executor(httpClient, serializer);
    }

    public void debug()
    {
        if(httpClientConnectionManager instanceof PoolingHttpClientConnectionManager)
            log.log(Level.INFO, ((PoolingHttpClientConnectionManager) httpClientConnectionManager).getTotalStats().toString());

        else
            log.log(Level.WARNING, "Cannot debug " + httpClientConnectionManager.getClass().getSimpleName() + " connection managers");
    }
}
