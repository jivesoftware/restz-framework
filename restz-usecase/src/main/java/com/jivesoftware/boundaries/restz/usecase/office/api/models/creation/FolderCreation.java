package com.jivesoftware.boundaries.restz.usecase.office.api.models.creation;

/**
 * Created by bmoshe on 8/20/14.
 */
public class FolderCreation
{
    private class Metadata
    {
        private String type = "SP.Folder";

        public String getType()
        {
            return type;
        }
    }

    private Metadata __metadata = new Metadata();
    private String ServerRelativeUrl;

    public Metadata get__metadata()
    {
        return __metadata;
    }

    public void set__metadata(Metadata __metadata)
    {
        this.__metadata = __metadata;
    }

    public String getServerRelativeUrl()
    {
        return ServerRelativeUrl;
    }

    public void setServerRelativeUrl(String serverRelativeUrl)
    {
        ServerRelativeUrl = serverRelativeUrl;
    }
}
