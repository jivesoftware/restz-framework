package com.jivesoftware.boundaries.restz.layers.basic;

import com.jivesoftware.boundaries.restz.RequestBuilder;
import com.jivesoftware.boundaries.restz.Response;
import com.jivesoftware.boundaries.restz.exceptions.FailureException;
import com.jivesoftware.boundaries.restz.layers.ExecutionWrapperLayer;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by bmoshe on 9/5/14.
 */
public abstract class BasicAuthLayer
implements ExecutionWrapperLayer
{
    protected abstract String getUsername();
    protected abstract String getPassword();

    @Override
    public void beforeExecution(RequestBuilder requestBuilder)
    {
        final String decrypedAuth = getUsername() + ":" + getPassword();
        final String encryptedAuth = asBase64(decrypedAuth);

        requestBuilder.addHeader("Authorization", "Basic " + encryptedAuth);
    }

    private String asBase64(String decrypedAuth)
    {
        final byte[] decrypedAuthAsByteArray = decrypedAuth.getBytes();
        final String encryptedAuth = DatatypeConverter.printBase64Binary(decrypedAuthAsByteArray);

        return encryptedAuth;
    }

    @Override
    public void afterExecution(RequestBuilder requestBuilder, Response response)
    throws FailureException
    {
    }
}
