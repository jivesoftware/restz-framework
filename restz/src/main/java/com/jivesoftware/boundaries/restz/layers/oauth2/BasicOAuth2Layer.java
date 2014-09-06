package com.jivesoftware.boundaries.restz.layers.oauth2;

import com.jivesoftware.boundaries.restz.HttpVerb;
import com.jivesoftware.boundaries.restz.RequestBuilder;

/**
 * Created by bmoshe on 8/19/14.
 */
public abstract class BasicOAuth2Layer
extends AbstractOAuth2Layer<OAuth2Client, OAuth2Token>
{
    @Override
    protected OAuth2Token refreshToken() {
        {
            RequestBuilder tokenRefreshRequestBuilder = new RequestBuilder(getOAuth2Url(), HttpVerb.POST)
                .setParam("refresh_token", getOAuth2Token().getRefresh_token())
                .setParam("grant_type", "refresh_token")
                .setParam("client_id", getOAuth2Client().getClientId())
                .setParam("client_secret", getOAuth2Client().getClientSecret());

            OAuth2Token token = getResTZ().execute(tokenRefreshRequestBuilder, OAuth2Token.class);
            setOAuth2Token(token);
            return token;
        }
    }
}
