package com.jivesoftware.boundaries.serializing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;

/**
 * Created by bmoshe on 8/25/14.
 */
public class GsonSerializer
implements Serializer
{
    // Given the moment of November 5, 1994, 8:15:30 AM, US Eastern Standard Time,
    // This date format will either match '1994-11-05T08:15:30-05:00' or '1994-11-05T13:15:30Z'.
    private static final String DF_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ssXXX";

    private Gson gson;

    public GsonSerializer()
    {
        this(DF_ISO_8601);
    }

    public GsonSerializer(String dateFormat)
    {
        this.gson = new GsonBuilder()
            .setDateFormat(dateFormat)
            .create();
    }

    @Override
    public String serialize(Object obj)
    {
        return gson.toJson(obj);
    }

    @Override
    public void serialize(Object obj, Writer writer)
    {
        gson.toJson(obj, writer);
    }

    @Override
    public <T> T deserialize(String serializedObj, Class<T> tClass)
    {
        return gson.fromJson(serializedObj, tClass);
    }

    @Override
    public <T> T deserialize(Reader reader, Class<T> tClass)
    {
        return gson.fromJson(reader, tClass);
    }
}
