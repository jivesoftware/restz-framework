package com.jivesoftware.boundaries.restz.layers.oauth2;

import com.jivesoftware.boundaries.restz.exceptions.FailureException;

/**
 * Created by bmoshe on 4/7/14.
 */
public class TokenExpiredException
extends FailureException
{
    private OAuth2Token oauth2Token;

    public TokenExpiredException(OAuth2Token oauth2Token)
    {
        this.oauth2Token = oauth2Token;
    }

    public OAuth2Token getOauth2Token()
    {
        return oauth2Token;
    }
}
