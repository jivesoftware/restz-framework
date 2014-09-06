package com.jivesoftware.boundaries.restz.usecase.office.api.models.creation;

import com.jivesoftware.boundaries.restz.usecase.office.api.models.common.DeferredResource;

/**
 * Created by bmoshe on 8/24/14.
 */
public class FileUploadResponse
{
    private Metadata __metadata = new Metadata();

    private DeferredResource Author;
    private DeferredResource CheckedOutByUser;
    private DeferredResource ListItemAllFields;
    private DeferredResource LockedByUser;
    private DeferredResource ModifiedBy;
    private DeferredResource Versions;

    private String CheckInComment;
    private int CheckOutType;
    private String ContentTag;
    private int CustomizedPageStatus;
    private String ETag;
    private boolean Exists;
    private long Length;
    private int Level;
    private String LinkingUrl;
    private int MajorVersion;
    private int MinorVersion;
    private String Name;
    private String ServerRelativeUrl;
    private String TimeCreated;
    private String TimeLastModified;
    private String Title;
    private int UIVersion;
    private String UIVersionLabel;
    private String UniqueId;

    public Metadata get__metadata()
    {
        return __metadata;
    }

    public void set__metadata(Metadata __metadata)
    {
        this.__metadata = __metadata;
    }

    public DeferredResource getAuthor()
    {
        return Author;
    }

    public void setAuthor(DeferredResource author)
    {
        Author = author;
    }

    public DeferredResource getCheckedOutByUser()
    {
        return CheckedOutByUser;
    }

    public void setCheckedOutByUser(DeferredResource checkedOutByUser)
    {
        CheckedOutByUser = checkedOutByUser;
    }

    public DeferredResource getListItemAllFields()
    {
        return ListItemAllFields;
    }

    public void setListItemAllFields(DeferredResource listItemAllFields)
    {
        ListItemAllFields = listItemAllFields;
    }

    public DeferredResource getLockedByUser()
    {
        return LockedByUser;
    }

    public void setLockedByUser(DeferredResource lockedByUser)
    {
        LockedByUser = lockedByUser;
    }

    public DeferredResource getModifiedBy()
    {
        return ModifiedBy;
    }

    public void setModifiedBy(DeferredResource modifiedBy)
    {
        ModifiedBy = modifiedBy;
    }

    public DeferredResource getVersions()
    {
        return Versions;
    }

    public void setVersions(DeferredResource versions)
    {
        Versions = versions;
    }

    public String getCheckInComment()
    {
        return CheckInComment;
    }

    public void setCheckInComment(String checkInComment)
    {
        CheckInComment = checkInComment;
    }

    public int getCheckOutType()
    {
        return CheckOutType;
    }

    public void setCheckOutType(int checkOutType)
    {
        CheckOutType = checkOutType;
    }

    public String getContentTag()
    {
        return ContentTag;
    }

    public void setContentTag(String contentTag)
    {
        ContentTag = contentTag;
    }

    public int getCustomizedPageStatus()
    {
        return CustomizedPageStatus;
    }

    public void setCustomizedPageStatus(int customizedPageStatus)
    {
        CustomizedPageStatus = customizedPageStatus;
    }

    public String getETag()
    {
        return ETag;
    }

    public void setETag(String ETag)
    {
        this.ETag = ETag;
    }

    public boolean isExists()
    {
        return Exists;
    }

    public void setExists(boolean exists)
    {
        Exists = exists;
    }

    public long getLength()
    {
        return Length;
    }

    public void setLength(long length)
    {
        Length = length;
    }

    public int getLevel()
    {
        return Level;
    }

    public void setLevel(int level)
    {
        Level = level;
    }

    public String getLinkingUrl()
    {
        return LinkingUrl;
    }

    public void setLinkingUrl(String linkingUrl)
    {
        LinkingUrl = linkingUrl;
    }

    public int getMajorVersion()
    {
        return MajorVersion;
    }

    public void setMajorVersion(int majorVersion)
    {
        MajorVersion = majorVersion;
    }

    public int getMinorVersion()
    {
        return MinorVersion;
    }

    public void setMinorVersion(int minorVersion)
    {
        MinorVersion = minorVersion;
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

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title)
    {
        Title = title;
    }

    public int getUIVersion()
    {
        return UIVersion;
    }

    public void setUIVersion(int UIVersion)
    {
        this.UIVersion = UIVersion;
    }

    public String getUIVersionLabel()
    {
        return UIVersionLabel;
    }

    public void setUIVersionLabel(String UIVersionLabel)
    {
        this.UIVersionLabel = UIVersionLabel;
    }

    public String getUniqueId()
    {
        return UniqueId;
    }

    public void setUniqueId(String uniqueId)
    {
        UniqueId = uniqueId;
    }

    public class Metadata
    {
        private String type = "SP.File";

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