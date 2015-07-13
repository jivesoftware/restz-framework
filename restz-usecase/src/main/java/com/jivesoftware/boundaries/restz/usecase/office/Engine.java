package com.jivesoftware.boundaries.restz.usecase.office;

import com.jivesoftware.boundaries.restz.Executor;
import com.jivesoftware.boundaries.restz.ExecutorFactory;
import com.jivesoftware.boundaries.restz.ResTZ;
import com.jivesoftware.boundaries.restz.hc450.HC450ExecutorFactory;
import com.jivesoftware.boundaries.restz.usecase.office.api.InstanceProperties;
import com.jivesoftware.boundaries.restz.usecase.office.api.OfficeApi;
import com.jivesoftware.boundaries.restz.usecase.office.api.models.creation.FileUploadResponse;
import com.jivesoftware.boundaries.restz.usecase.office.api.models.creation.FolderCreationResponse;
import com.jivesoftware.boundaries.restz.usecase.office.api.models.creation.SiteCreationResponse;
import com.jivesoftware.boundaries.restz.usecase.office.restz.OfficeOAuth2Client;
import com.jivesoftware.boundaries.restz.usecase.office.restz.OfficeOAuth2Token;
import org.apache.commons.io.IOUtils;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by bmoshe on 8/13/14.
 */
public class Engine
{
    private static Logger log = Logger.getLogger(Engine.class.getSimpleName());

    public static void main(String[] args)
    {
        // Injection Code
        final HttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        final ExecutorFactory executorFactory = new HC450ExecutorFactory(httpClientConnectionManager);

        final InstanceProperties instanceProperties = prepareInstanceProperties();

        final OfficeOAuth2Client client = prepareOAuth2Client();
        final OfficeOAuth2Token token = prepareOAuth2Token();

        // ResTZ Example Code
        final Executor executor = executorFactory.get();
        final ResTZ restz = new ResTZ(executor);

        final OfficeApi officeApi = new OfficeApi(instanceProperties, restz, client, token);

        final SiteCreationResponse site = officeApi.createSite("my-site");
        final String siteRelativeUrl = site.getServerRelativeUrl();

        final FolderCreationResponse folder = officeApi.createFolder(siteRelativeUrl, "my-folder");

        final String folderId = folder.getUniqueId();
        uploadAndDownloadFile(officeApi, siteRelativeUrl, folderId, "/test/test.zip", "/test/test-downloaded.zip");

    }

    private static void uploadAndDownloadFile(OfficeApi officeApi, String siteRelativeUrl, String folderId, String uploadFileName, String downloadFileName)
    {
        try
        {
            final FileUploadResponse file = officeApi.uploadFile(siteRelativeUrl, folderId, uploadFileName);
            downloadFile(officeApi, siteRelativeUrl, file.getUniqueId(), downloadFileName);
        }
        catch (IOException e)
        {
            log.log(Level.SEVERE, "Could not upload file - " + e.getMessage());
        }
    }

    private static InstanceProperties prepareInstanceProperties()
    {
        InstanceProperties instanceProperties = new InstanceProperties();

        instanceProperties.setUrl("https://example.sharepoint.com");

        return instanceProperties;
    }

    private static OfficeOAuth2Client prepareOAuth2Client()
    {
        OfficeOAuth2Client client = new OfficeOAuth2Client();

        client.setClientId("OFFICE_CLIENT_ID");
        client.setClientSecret("OFFICE_CLIENT_SECRET");

        client.setServiceUrl("OFFICE_SERVICE_URL");

        return client;
    }

    private static OfficeOAuth2Token prepareOAuth2Token()
    {
        OfficeOAuth2Token token = new OfficeOAuth2Token();

        token.setAccess_token("OFFICE_ACCESS_TOKEN");
        token.setRefresh_token("OFFICE_REFRESH_TOKEN");

        token.setResource("OFFICE_RESOURCE");

        return token;
    }

    private static void downloadFile(OfficeApi officeApi, String siteRelativeUrl, String officeFileId, String fileName)
    {
        try
        (
            InputStream in = officeApi.downloadFile(siteRelativeUrl, officeFileId);
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