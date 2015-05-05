package com.jivesoftware.boundaries.restz.hc400;

import com.jivesoftware.boundaries.restz.Executor;
import com.jivesoftware.boundaries.restz.ExecutorFactory;
import com.jivesoftware.boundaries.serializing.Serializer;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by bmoshe on 9/1/14.
 */
public class HC400ExecutorFactory
implements ExecutorFactory
{
    private final static Logger log = Logger.getLogger(HC400ExecutorFactory.class.getSimpleName());

    protected final ClientConnectionManager clientConnectionManager;
    protected final Serializer serializer;

    public HC400ExecutorFactory(ClientConnectionManager clientConnectionManager, Serializer serializer)
    {
        this.clientConnectionManager = clientConnectionManager;
        this.serializer = serializer;
    }

    public Executor get()
    {
        final HttpClient httpClient = new DefaultHttpClient(clientConnectionManager, new BasicHttpParams());

        final Executor executor = new HC400Executor(httpClient, serializer);
        return executor;
    }

    public void debug()
    {
        if(clientConnectionManager instanceof ThreadSafeClientConnManager)
        {
            int connectionsInPool = ((ThreadSafeClientConnManager) clientConnectionManager).getConnectionsInPool();
            log.log(Level.INFO, "There are " + connectionsInPool + " connections in pool");
        }
        else
            log.log(Level.WARNING, "Cannot debug " + clientConnectionManager.getClass().getSimpleName() + " connection managers");
    }
}
