package com.jivesoftware.boundaries.restz.strategies;

import com.jivesoftware.boundaries.restz.Response;
import com.jivesoftware.boundaries.restz.ResponseReadingStrategy;
import com.jivesoftware.boundaries.serializing.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by bmoshe on 4/2/14.
 */
public class SerializerReadingStrategy
implements ResponseReadingStrategy
{
    private final Serializer serializer;

    public SerializerReadingStrategy(Serializer serializer)
    {
        this.serializer = serializer;
    }

    @Override
    public <T> T read(Response response, Class<T> tClass)
    throws IOException
    {
        if (tClass == void.class)
        {
            response.close();
            return null;
        }

        InputStream in = response.getContent();

        if (in == null)
        {
            response.close();
            return null;
        }

        try
        (
            InputStreamReader reader = new InputStreamReader(in)
        )
        {
            T entity = serializer.deserialize(reader, tClass);
            return entity;
        }
        finally
        {
            response.close();
        }
    }
}