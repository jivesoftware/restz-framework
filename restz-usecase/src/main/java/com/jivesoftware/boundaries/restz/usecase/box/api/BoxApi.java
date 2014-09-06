package com.jivesoftware.boundaries.restz.usecase.box.api;

import com.jivesoftware.boundaries.restz.*;
import com.jivesoftware.boundaries.restz.RequestBuilder;
import com.jivesoftware.boundaries.restz.exceptions.FailureException;
import com.jivesoftware.boundaries.restz.LayerCollection;
import com.jivesoftware.boundaries.restz.layers.oauth2.BasicOAuth2Layer;
import com.jivesoftware.boundaries.restz.layers.oauth2.OAuth2Client;
import com.jivesoftware.boundaries.restz.layers.oauth2.OAuth2Token;
import com.jivesoftware.boundaries.restz.strategies.SmartReadingStrategy;
import com.jivesoftware.boundaries.restz.usecase.box.api.models.BoxCreateFolderRequestObject;
import com.jivesoftware.boundaries.restz.usecase.box.api.models.BoxFile;
import com.jivesoftware.boundaries.restz.usecase.box.api.models.BoxFileCollection;
import com.jivesoftware.boundaries.restz.usecase.box.api.models.BoxFolder;
import com.jivesoftware.boundaries.restz.ResTZ;
import com.jivesoftware.boundaries.restz.multipart.MultipartEntityBuilder;

import java.io.File;
import java.io.InputStream;

/**
 * Created by bmoshe on 4/2/14.
 */
public class BoxApi
{
    private ResTZ restz;
    private LayerCollection layers;

    private OAuth2Token token;
    private OAuth2Client client;

    public BoxApi(ResTZ restz, OAuth2Client client, OAuth2Token token)
    {
        this.restz = restz;
        this.layers = new LayerCollection(new BoxOAuth2Layer());

        this.client = client;
        this.token = token;

    }

    public BoxFolder createFolder(String folderName, String parentFolderId)
    {
        RequestBuilder requestBuilder = new RequestBuilder("https://api.box.com/2.0/folders", HttpVerb.POST)
            .setEntity(new BoxCreateFolderRequestObject(folderName, parentFolderId));

        return restz.execute(requestBuilder, layers, ResponseReadingStrategy.gsonResponseReadingStrategy, BoxFolder.class);
    }

    public void deleteFolder(String id)
    {
        RequestBuilder requestBuilder = new RequestBuilder("https://api.box.com/2.0/folders/" + id + "?recursive=true", HttpVerb.DELETE);
        restz.execute(requestBuilder, layers, new SmartReadingStrategy(), void.class);
    }

    public BoxFile uploadFile(String boxParentFolderId, File file)
    {
        RequestBuilder requestBuilder = new RequestBuilder("https://api.box.com/2.0/files/content", HttpVerb.POST);

        final MultipartEntityBuilder multipartEntityBuilder = new MultipartEntityBuilder()
            .addBinaryPart("file", file)
            .addTextPart("filename", file.getName())
            .addTextPart("parent_id", boxParentFolderId);

        requestBuilder.setEntity(multipartEntityBuilder);

        final BoxFileCollection boxFileCollection = restz.execute(requestBuilder, layers, new SmartReadingStrategy(), BoxFileCollection.class);
        return boxFileCollection.getEntries()[0];
    }

    public InputStream downloadFile(String boxFileId)
    {
        RequestBuilder requestBuilder = new RequestBuilder("https://api.box.com/2.0/files/" + boxFileId + "/content", HttpVerb.GET);
        return restz.execute(requestBuilder, layers, new SmartReadingStrategy(), InputStream.class);
    }

    private class BoxOAuth2Layer
    extends BasicOAuth2Layer
    {
        @Override
        public ResTZ getResTZ()
        {
            return BoxApi.this.restz;
        }

        @Override
        public OAuth2Client getOAuth2Client()
        {
            return BoxApi.this.client;
        }

        @Override
        public OAuth2Token getOAuth2Token()
        {
            return BoxApi.this.token;
        }

        @Override
        public void setOAuth2Token(OAuth2Token token)
        {
            BoxApi.this.token = token;
        }

        @Override
        public String getOAuth2Url()
        {
            return "https://api.box.com/oauth2/token";
        }

        @Override
        public void afterExecution(RequestBuilder requestBuilder, Response response)
        throws FailureException
        {
        }
    }
}