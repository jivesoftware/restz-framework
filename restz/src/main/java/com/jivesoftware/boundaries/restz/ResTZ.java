package com.jivesoftware.boundaries.restz;

import com.jivesoftware.boundaries.restz.exceptions.CheckedAsRuntimeException;
import com.jivesoftware.boundaries.restz.exceptions.FailureException;
import com.jivesoftware.boundaries.restz.exceptions.HttpFailureException;
import com.jivesoftware.boundaries.restz.layers.ExecutionWrapperLayer;
import com.jivesoftware.boundaries.restz.layers.RecoverableFailureLayer;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by bmoshe on 4/1/14.
 */
public class ResTZ
{
    private static Logger log = Logger.getLogger(ResTZ.class.getSimpleName());

    private Executor executor;

    private LayerCollection layers;
    private ResponseReadingStrategy responseReadingStrategy;

    public ResTZ(Executor executor)
    {
        this(executor, ResponseReadingStrategy.smartResponseReadingStrategy);
    }

    public ResTZ(Executor executor, ResponseReadingStrategy responseReadingStrategy)
    {
        this(executor, new LayerCollection(), responseReadingStrategy);
    }

    public ResTZ(Executor executor, LayerCollection layers, ResponseReadingStrategy responseReadingStrategy)
    {
        this.executor = executor;

        this.layers = layers;
        this.responseReadingStrategy = responseReadingStrategy;
    }

    public ResTZ(Executor executor, LayerCollection layers)
    {
        this(executor, layers, ResponseReadingStrategy.smartResponseReadingStrategy);
    }

    public <T> T execute(RequestBuilder requestBuilder, Class<T> tClass)
    {
        return execute(requestBuilder, layers, responseReadingStrategy, tClass);
    }

    public <T> T execute(RequestBuilder requestBuilder, ResponseReadingStrategy responseReadingStrategy, Class<T> tClass)
    {
        return execute(requestBuilder, layers, responseReadingStrategy, tClass);
    }

    public <T> T execute(RequestBuilder requestBuilder, LayerCollection layers, Class<T> tClass)
    {
        return execute(requestBuilder, layers, responseReadingStrategy, tClass);
    }

    public <T> T execute(RequestBuilder requestBuilder, LayerCollection layers, ResponseReadingStrategy responseReadingStrategy, Class<T> tClass)
    {
        Response response = null;

        try
        {
            for(ExecutionWrapperLayer wrapper : layers.getWrappers())
                wrapper.beforeExecution(requestBuilder);

            log.log(Level.INFO, "Executing " + requestBuilder.getHttpVerb() + " " + requestBuilder.getUrl() + " with layer");
            response = executor.execute(requestBuilder);

            detectFailures(requestBuilder, layers, response);

            for(ExecutionWrapperLayer wrapper : layers.getWrappers())
                wrapper.afterExecution(requestBuilder, response);

            T responseEntity = responseReadingStrategy.read(response, tClass);
            return responseEntity;
        }
        catch(FailureException e)
        {
            boolean hasRecovered = tryRecoverFailure(requestBuilder, layers, response, e);
            if(hasRecovered)
                return execute(requestBuilder, layers, responseReadingStrategy, tClass);

            throw e;
        }
        catch(IOException e)
        {
            throw new CheckedAsRuntimeException("Failed to process request " + requestBuilder.getUrl() + " - " + e.getMessage(), e);
        }
    }

    private void detectFailures(RequestBuilder requestBuilder, LayerCollection layers, Response response)
    {
        try
        {
            final boolean hasHttpFailures = detectHttpFailures(response);
            if(hasHttpFailures)
                throwHttpFailure(response);
        }
        catch(HttpFailureException e)
        {
            for (RecoverableFailureLayer recoverable : layers.getRecoverables())
                recoverable.hasFailed(requestBuilder, e);

            throw e;
        }
    }

    private boolean detectHttpFailures(Response response)
    {
        Status.Family statusFamily = response.getStatus().getFamily();

        boolean hasClientError = Status.Family.CLIENT_ERROR.equals(statusFamily);
        boolean hasServerError = Status.Family.SERVER_ERROR.equals(statusFamily);

        return hasClientError || hasServerError;
    }

    private void throwHttpFailure(Response response)
    throws HttpFailureException
    {
        try
        {
            ResponseEntity<String> responseEntity = responseReadingStrategy.read(response, StringResponseEntity.class);
            throw new HttpFailureException(responseEntity);
        }
        catch (IOException e)
        {
            final String message = "Failed to parse HTTP error response as String - " + e.getMessage();
            throw new CheckedAsRuntimeException(message, e);
        }
    }

    private boolean tryRecoverFailure(RequestBuilder requestBuilder, LayerCollection layers, Response response, FailureException exception)
    {
        for(RecoverableFailureLayer recoverable : layers.getRecoverables())
        {
            boolean hasRecovered = recoverable.recover(requestBuilder, exception);
            if (hasRecovered)
                return true;
        }

        return false;
    }

    private class StringResponseEntity
    extends ResponseEntity<String>
    {
        public StringResponseEntity(String entity, Status status, MultivaluedMap<String, String> headers)
        {
            super(entity, status, headers);
        }
    }
}