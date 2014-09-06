package com.jivesoftware.boundaries.restz.usecase.office.api.models.common;

/**
 * Created by bmoshe on 8/20/14.
 */
public class DigestContext
{
    private ContextWebInformation GetContextWebInformation;

    public ContextWebInformation getGetContextWebInformation()
    {
        return GetContextWebInformation;
    }

    public void setGetContextWebInformation(ContextWebInformation getContextWebInformation)
    {
        GetContextWebInformation = getContextWebInformation;
    }

    public class ContextWebInformation
    {
        private String FormDigestValue;

        public String getFormDigestValue()
        {
            return FormDigestValue;
        }

        public void setFormDigestValue(String formDigestValue)
        {
            FormDigestValue = formDigestValue;
        }
    }
}
