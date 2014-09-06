package com.jivesoftware.boundaries.restz.layers.oauth2;

import com.jivesoftware.boundaries.restz.RequestBuilder;
import com.jivesoftware.boundaries.restz.ResTZ;
import com.jivesoftware.boundaries.restz.exceptions.FailureException;
import com.jivesoftware.boundaries.restz.exceptions.HttpFailureException;
import com.jivesoftware.boundaries.restz.layers.ExecutionWrapperLayer;
import com.jivesoftware.boundaries.restz.layers.RecoverableFailureLayer;

import javax.ws.rs.core.Response.Status;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by bmoshe on 4/7/14.
 */
public abstract class AbstractOAuth2Layer<C extends OAuth2Client, T extends OAuth2Token>
implements ExecutionWrapperLayer, RecoverableFailureLayer
{
    private static Logger log = Logger.getLogger(AbstractOAuth2Layer.class.getSimpleName());

    public abstract ResTZ getResTZ();

    public abstract C getOAuth2Client();

    public abstract T getOAuth2Token();
    public abstract void setOAuth2Token(T oauth2Token);

    public abstract String getOAuth2Url();

    @Override
    public void beforeExecution(RequestBuilder requestBuilder)
    {
        requestBuilder.clearHeader("Authorization");
        requestBuilder.addHeader("Authorization", "Bearer " + getOAuth2Token().getAccess_token());
    }

    @Override
    public void hasFailed(RequestBuilder requestBuilder, HttpFailureException exception)
    throws FailureException
    {
        if(Status.UNAUTHORIZED.equals(exception.getStatus()))
            throw new TokenExpiredException(getOAuth2Token());
    }

    @Override
    public boolean recover(RequestBuilder requestBuilder, FailureException exception)
    {
        if (exception instanceof TokenExpiredException)
        {
            try
            {
                T token = refreshToken();
                log.log(Level.INFO, token.getAccess_token() + " | " + token.getRefresh_token());

                setOAuth2Token(token);

                return true;
            }
            catch (HttpFailureException e)
            {
                if(!Status.OK.equals(e.getStatus()))
                    throw new TokenRevokedException();

                return false;
            }
        }

        return false;
    }

    protected abstract T refreshToken();
}