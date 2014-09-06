package com.jivesoftware.boundaries.restz.usecase.office.api.models.creation;

/**
 * Created by bmoshe on 8/24/14.
 */
public class SiteCreation
{
    private Parameters parameters = new Parameters();

    public Parameters getParameters()
    {
        return parameters;
    }

    public void setParameters(Parameters parameters)
    {
        this.parameters = parameters;
    }

    public class Parameters
    {
        private Metadata __metadata = new Metadata();

        private String Url;
        private String Title;
        private String Description;
        private int Language;
        private String WebTemplate;
        private boolean UseUniquePermissions;
        public Metadata get__metadata()
        {
            return __metadata;
        }

        public void set__metadata(Metadata __metadata)
        {
            this.__metadata = __metadata;
        }

        public String getUrl()
        {
            return Url;
        }

        public void setUrl(String url)
        {
            Url = url;
        }

        public String getTitle()
        {
            return Title;
        }

        public void setTitle(String title)
        {
            Title = title;
        }

        public String getDescription()
        {
            return Description;
        }

        public void setDescription(String description)
        {
            Description = description;
        }

        public int getLanguage()
        {
            return Language;
        }

        public void setLanguage(int language)
        {
            Language = language;
        }

        public String getWebTemplate()
        {
            return WebTemplate;
        }

        public void setWebTemplate(String webTemplate)
        {
            WebTemplate = webTemplate;
        }

        public boolean isUseUniquePermissions()
        {
            return UseUniquePermissions;
        }

        public void setUseUniquePermissions(boolean useUniquePermissions)
        {
            UseUniquePermissions = useUniquePermissions;
        }

        public class Metadata
        {
            private String type = "SP.WebInfoCreationInformation";

            public String getType()
            {
                return type;
            }
        }
    }
}
