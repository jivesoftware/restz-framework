package com.jivesoftware.boundaries.restz.usecase.office.restz;

import com.jivesoftware.boundaries.restz.layers.oauth2.OAuth2Client;

/**
 * Created by bmoshe on 8/13/14.
 */
public class OfficeOAuth2Client
extends OAuth2Client
{
    private String serviceUrl;

    public String getServiceUrl()
    {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl)
    {
        this.serviceUrl = serviceUrl;
    }
}
