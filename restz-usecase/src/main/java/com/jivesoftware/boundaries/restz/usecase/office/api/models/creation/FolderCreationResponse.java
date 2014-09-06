package com.jivesoftware.boundaries.restz.usecase.office.api.models.creation;

import com.jivesoftware.boundaries.restz.usecase.office.api.models.common.DeferredResource;

/**
 * Created by bmoshe on 8/24/14.
 */
public class FolderCreationResponse
{
    private Metadata __metadata = new Metadata();

    private DeferredResource Files;
    private DeferredResource ListItemAllFields;
    private DeferredResource ParentFolder;
    private DeferredResource Properties;
    private DeferredResource Folders;

    private int ItemCount;
    private String Name;
    private String ServerRelativeUrl;
    private String TimeCreated;
    private String TimeLastModified;
    private String UniqueId;
    private String WelcomePage;

    public Metadata get__metadata()
    {
        return __metadata;
    }

    public void set__metadata(Metadata __metadata)
    {
        this.__metadata = __metadata;
    }

    public DeferredResource getFiles()
    {
        return Files;
    }

    public void setFiles(DeferredResource files)
    {
        Files = files;
    }

    public DeferredResource getListItemAllFields()
    {
        return ListItemAllFields;
    }

    public void setListItemAllFields(DeferredResource listItemAllFields)
    {
        ListItemAllFields = listItemAllFields;
    }

    public DeferredResource getParentFolder()
    {
        return ParentFolder;
    }

    public void setParentFolder(DeferredResource parentFolder)
    {
        ParentFolder = parentFolder;
    }

    public DeferredResource getProperties()
    {
        return Properties;
    }

    public void setProperties(DeferredResource properties)
    {
        Properties = properties;
    }

    public DeferredResource getFolders()
    {
        return Folders;
    }

    public void setFolders(DeferredResource folders)
    {
        Folders = folders;
    }

    public int getItemCount()
    {
        return ItemCount;
    }

    public void setItemCount(int itemCount)
    {
        ItemCount = itemCount;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String name)
    {
        Name = name;
    }

    public String getServerRelativeUrl()
    {
        return ServerRelativeUrl;
    }

    public void setServerRelativeUrl(String serverRelativeUrl)
    {
        ServerRelativeUrl = serverRelativeUrl;
    }

    public String getTimeCreated()
    {
        return TimeCreated;
    }

    public void setTimeCreated(String timeCreated)
    {
        TimeCreated = timeCreated;
    }

    public String getTimeLastModified()
    {
        return TimeLastModified;
    }

    public void setTimeLastModified(String timeLastModified)
    {
        TimeLastModified = timeLastModified;
    }

    public String getUniqueId()
    {
        return UniqueId;
    }

    public void setUniqueId(String uniqueId)
    {
        UniqueId = uniqueId;
    }

    public String getWelcomePage()
    {
        return WelcomePage;
    }

    public void setWelcomePage(String welcomePage)
    {
        WelcomePage = welcomePage;
    }

    public class Metadata
    {
        private String type = "SP.Folder";

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }
    }
}
