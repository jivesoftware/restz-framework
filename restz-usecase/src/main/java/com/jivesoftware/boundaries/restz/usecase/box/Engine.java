package com.jivesoftware.boundaries.restz.usecase.box;

import com.jivesoftware.boundaries.restz.Executor;
import com.jivesoftware.boundaries.restz.ExecutorFactory;
import com.jivesoftware.boundaries.restz.ResTZ;
import com.jivesoftware.boundaries.restz.hc450.HC450ExecutorFactory;
import com.jivesoftware.boundaries.restz.layers.oauth2.OAuth2Client;
import com.jivesoftware.boundaries.restz.layers.oauth2.OAuth2Token;
import com.jivesoftware.boundaries.restz.usecase.box.api.BoxApi;
import com.jivesoftware.boundaries.restz.usecase.box.api.models.BoxFile;
import com.jivesoftware.boundaries.restz.usecase.box.api.models.BoxFolder;
import org.apache.commons.io.IOUtils;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by bmoshe on 4/1/14.
 */
public final class Engine
{
    private static Logger log = Logger.getLogger(Engine.class.getSimpleName());

    public static void main(String[] args)
    {
        // Injection Code
        final HttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        final ExecutorFactory executorFactory = new HC450ExecutorFactory(httpClientConnectionManager);

        final OAuth2Client client = prepareOAuth2Client();
        final OAuth2Token token = prepareOAuth2Token();

        // ResTZ Example Code
        final Executor executor = executorFactory.get();
        final ResTZ restz = new ResTZ(executor);
        final BoxApi boxApi = new BoxApi(restz, client, token);

        final BoxFolder boxFolder = boxApi.createFolder("top-level-folder", "0");
        final BoxFile boxFile = boxApi.uploadFile(boxFolder.getId(), new File("/test/test.zip"));
        downloadFile(boxApi, boxFile.getId(), "/test/test-downloaded.zip");
    }

    private static OAuth2Client prepareOAuth2Client()
    {
        OAuth2Client client = new OAuth2Client();

        client.setClientId("BOX_CLIENT_ID");
        client.setClientSecret("BOX_CLIENT_SECRET");

        return client;
    }

    private static OAuth2Token prepareOAuth2Token()
    {
        OAuth2Token token = new OAuth2Token();

        token.setAccess_token("BOX_ACCESS_TOKEN");
        token.setRefresh_token("BOX_REFRESH_TOKEN");

        return token;
    }

    private static void downloadFile(BoxApi boxApi, String boxFileId, String fileName)
    {
        try
        (
            InputStream in = boxApi.downloadFile(boxFileId);
            FileOutputStream out = new FileOutputStream(fileName);
        )
        {
            IOUtils.copyLarge(in, out);
        }
        catch (Exception e)
        {
            log.log(Level.WARNING, "Could not download file - " + e.getMessage(), e);
        }
    }
}