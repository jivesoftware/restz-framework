package com.jivesoftware.boundaries.restz.usecase.office.api;

import com.jivesoftware.boundaries.restz.*;
import com.jivesoftware.boundaries.restz.exceptions.FailureException;
import com.jivesoftware.boundaries.restz.layers.ExecutionWrapperLayer;
import com.jivesoftware.boundaries.restz.layers.oauth2.AbstractOAuth2Layer;
import com.jivesoftware.boundaries.restz.strategies.SmartReadingStrategy;
import com.jivesoftware.boundaries.restz.strategies.SerializerReadingStrategy;
import com.jivesoftware.boundaries.restz.usecase.office.api.models.common.DigestContext;
import com.jivesoftware.boundaries.restz.usecase.office.api.models.creation.*;
import com.jivesoftware.boundaries.restz.usecase.office.restz.DStrippingGsonSerializer;
import com.jivesoftware.boundaries.restz.usecase.office.restz.OfficeOAuth2Client;
import com.jivesoftware.boundaries.restz.usecase.office.restz.OfficeOAuth2Token;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.core.MultivaluedMap;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bmoshe on 8/13/14.
 */
public class OfficeApi
{
    private final static ResponseReadingStrategy dStrippingSimpleReadingStrategy = new SmartReadingStrategy(new SerializerReadingStrategy(new DStrippingGsonSerializer()));

    private InstanceProperties instanceProperties;

    private ResTZ restz;
    private LayerCollection layers;

    private OfficeOAuth2Token token;
    private OfficeOAuth2Client client;

    public OfficeApi(InstanceProperties instanceProperties, ResTZ restz, OfficeOAuth2Client client, OfficeOAuth2Token token)
    {
        this.instanceProperties = instanceProperties;

        this.restz = restz;
        this.layers = new LayerCollection(new OfficeHeadersLayer(), new OfficeOAuth2Layer());

        this.client = client;
        this.token = token;
    }

    public SiteCreationResponse createSite(String siteName)
    {
        final SiteCreation siteCreation = new SiteCreation();

        final String siteUrl = siteName.toLowerCase().replace(" ", "-");
        siteCreation.getParameters().setUrl(siteUrl);

        siteCreation.getParameters().setTitle(siteName);
        siteCreation.getParameters().setDescription(siteName + "'s Description");
        siteCreation.getParameters().setLanguage(1033);
        siteCreation.getParameters().setWebTemplate("sts");
        siteCreation.getParameters().setUseUniquePermissions(false);

        RequestBuilder requestBuilder = new RequestBuilder(instanceProperties.getUrl() + "/_api/web/webinfos/add", HttpVerb.POST)
            .setEntity(siteCreation);

        return restz.execute(requestBuilder, layers, dStrippingSimpleReadingStrategy, SiteCreationResponse.class);
    }

    public FolderCreationResponse createFolder(String siteRelativeUrl, String folderName)
    {
        final FolderCreation folderCreation = new FolderCreation();
        folderCreation.setServerRelativeUrl("Shared Documents/" + folderName);

        RequestBuilder requestBuilder = new RequestBuilder(instanceProperties.getUrl() + siteRelativeUrl + "/_api/web/folders", HttpVerb.POST)
                .setEntity(folderCreation);

        return restz.execute(requestBuilder, layers, dStrippingSimpleReadingStrategy, FolderCreationResponse.class);
    }

    public FileUploadResponse uploadFile(String siteRelativeUrl, String folderId, String fileName)
    throws IOException
    {
        try
        (
            FileInputStream fis = new FileInputStream(fileName)
        )
        {
            RequestBuilder requestBuilder = new RequestBuilder(instanceProperties.getUrl() + siteRelativeUrl + "/_api/web/GetFolderById('" + folderId + "')/Files/add(url='" + fileName.replaceAll(" ", "%20") + "',overwrite=true)", HttpVerb.POST)
                .addHeader("binaryStringRequestBody", "true");

            byte[] bytes = IOUtils.toByteArray(fis);
            requestBuilder.setEntity(bytes);

            return restz.execute(requestBuilder, layers, dStrippingSimpleReadingStrategy, FileUploadResponse.class);
        }
    }

    public InputStream downloadFile(String siteRelativeUrl, String fileId)
    {
        RequestBuilder requestBuilder = new RequestBuilder(instanceProperties.getUrl() + siteRelativeUrl + "/_api/web/GetFileById('" + fileId + "')/$value", HttpVerb.GET)
            .addHeader("binaryStringRequestBody", "true");

        return restz.execute(requestBuilder, layers, InputStream.class);
    }

    private class OfficeHeadersLayer
    implements ExecutionWrapperLayer
    {

        @Override
        public void beforeExecution(RequestBuilder requestBuilder)
        {
            requestBuilder.addHeader("Content-Type", "application/json;odata=verbose");
            requestBuilder.addHeader("Accept", "application/json;odata=verbose");
        }

        @Override
        public void afterExecution(RequestBuilder requestBuilder, Response response)
        throws FailureException
        {
        }

    }

    private class OfficeOAuth2Layer
    extends AbstractOAuth2Layer<OfficeOAuth2Client, OfficeOAuth2Token>
    {
        @Override
        public ResTZ getResTZ()
        {
            return OfficeApi.this.restz;
        }

        @Override
        public OfficeOAuth2Client getOAuth2Client()
        {
            return OfficeApi.this.client;
        }

        @Override
        public OfficeOAuth2Token getOAuth2Token()
        {
            return OfficeApi.this.token;
        }

        @Override
        public void setOAuth2Token(OfficeOAuth2Token token)
        {
            OfficeApi.this.token = token;
        }

        @Override
        public String getOAuth2Url()
        {
            return getOAuth2Client().getServiceUrl();
        }

        @Override
        public void beforeExecution(RequestBuilder requestBuilder)
        {
            super.beforeExecution(requestBuilder);
        }

        @Override
        public void afterExecution(RequestBuilder requestBuilder, Response response)
        throws FailureException
        {
        }

        @Override
        protected OfficeOAuth2Token refreshToken()
        {
            RequestBuilder requestBuilder = new RequestBuilder(getOAuth2Url(), HttpVerb.POST)
                    .setParam("refresh_token", getOAuth2Token().getRefresh_token())
                    .setParam("grant_type", "refresh_token")
                    .setParam("client_id", getOAuth2Client().getClientId())
                    .setParam("client_secret", getOAuth2Client().getClientSecret())
                    .setParam("resource", getOAuth2Token().getResource());


            ResponseEntity<OfficeOAuth2Token> tokenResponseEntity = restz.execute(requestBuilder, OfficeOAuth2TokenResponseEntity.class);
            OfficeOAuth2Token token = tokenResponseEntity.getEntity();

            // Override with previous values
            token.setRefresh_token(getOAuth2Token().getRefresh_token());
            return token;
        }
    }

    private class OfficeOAuth2TokenResponseEntity
    extends ResponseEntity<OfficeOAuth2Token>
    {
        public OfficeOAuth2TokenResponseEntity(OfficeOAuth2Token entity, javax.ws.rs.core.Response.Status status, MultivaluedMap<String, String> headers)
        {
            super(entity, status, headers);
        }
    }

    // According to Microsoft's documentation, this layer may be required for some operations.
    // So far, every operation I tried didn't need it.
    private class OfficeDigestLayer
    implements ExecutionWrapperLayer
    {
        public ResTZ getResTZ()
        {
            return OfficeApi.this.restz;
        }

        @Override
        public void beforeExecution(RequestBuilder requestBuilder)
        {
            RequestBuilder digestRequestBuilder = new RequestBuilder(instanceProperties.getUrl() + "/_api/contextinfo", HttpVerb.POST);

            final LayerCollection digestLayers = new LayerCollection(new OfficeHeadersLayer(), new OfficeOAuth2Layer());
            DigestContext digestContext = getResTZ().execute(digestRequestBuilder, digestLayers, dStrippingSimpleReadingStrategy, DigestContext.class);

            String digestValue = digestContext.getGetContextWebInformation().getFormDigestValue();
            requestBuilder.addHeader("X-RequestDigest", digestValue);
        }

        @Override
        public void afterExecution(RequestBuilder requestBuilder, Response response)
        throws FailureException
        {

        }
    }
}