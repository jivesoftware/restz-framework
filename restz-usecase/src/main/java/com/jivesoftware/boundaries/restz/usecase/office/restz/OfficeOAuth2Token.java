package com.jivesoftware.boundaries.restz.usecase.office.restz;

import com.jivesoftware.boundaries.restz.layers.oauth2.OAuth2Token;

/**
 * Created by bmoshe on 8/13/14.
 */
public class OfficeOAuth2Token
extends OAuth2Token
{
    private String resource;

    public String getResource()
    {
        return resource;
    }

    public void setResource(String resource)
    {
        this.resource = resource;
    }
}
