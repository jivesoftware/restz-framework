package com.jivesoftware.boundaries.restz.usecase.office.api.models.creation;

/**
 * Created by bmoshe on 8/24/14.
 */
public class SiteCreationResponse
{
    private Metadata __metadata;

    private int Configuration;
    private String Created;
    private String Description;
    private String Id;
    private int Language;
    private String LastItemModifiedDate;
    private String ServerRelativeUrl;
    private String Title;
    private String WebTemplate;
    private int WebTemplateId;

    public Metadata get__metadata()
    {
        return __metadata;
    }

    public void set__metadata(Metadata __metadata)
    {
        this.__metadata = __metadata;
    }

    public int getConfiguration()
    {
        return Configuration;
    }

    public void setConfiguration(int configuration)
    {
        Configuration = configuration;
    }

    public String getCreated()
    {
        return Created;
    }

    public void setCreated(String created)
    {
        Created = created;
    }

    public String getDescription()
    {
        return Description;
    }

    public void setDescription(String description)
    {
        Description = description;
    }

    public String getId()
    {
        return Id;
    }

    public void setId(String id)
    {
        Id = id;
    }

    public int getLanguage()
    {
        return Language;
    }

    public void setLanguage(int language)
    {
        Language = language;
    }

    public String getLastItemModifiedDate()
    {
        return LastItemModifiedDate;
    }

    public void setLastItemModifiedDate(String lastItemModifiedDate)
    {
        LastItemModifiedDate = lastItemModifiedDate;
    }

    public String getServerRelativeUrl()
    {
        return ServerRelativeUrl;
    }

    public void setServerRelativeUrl(String serverRelativeUrl)
    {
        ServerRelativeUrl = serverRelativeUrl;
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title)
    {
        Title = title;
    }

    public String getWebTemplate()
    {
        return WebTemplate;
    }

    public void setWebTemplate(String webTemplate)
    {
        WebTemplate = webTemplate;
    }

    public int getWebTemplateId()
    {
        return WebTemplateId;
    }

    public void setWebTemplateId(int webTemplateId)
    {
        WebTemplateId = webTemplateId;
    }

    public class Metadata
    {
        private String type = "SP.WebInformation";

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
