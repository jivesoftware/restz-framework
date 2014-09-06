package com.jivesoftware.boundaries.restz.usecase.office.restz;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jivesoftware.boundaries.serializing.Serializer;

import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

/**
 * Created by bmoshe on 9/1/14.
 */
public class DStrippingGsonSerializer
implements Serializer
{
    public Gson gson;

    public DStrippingGsonSerializer()
    {
        this.gson = new Gson();
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
        final StringReader reader = new StringReader(serializedObj);

        final JsonObject innerObject = new JsonParser().parse(reader).getAsJsonObject().getAsJsonObject("d");
        return gson.fromJson(innerObject, tClass);
    }

    @Override
    public <T> T deserialize(Reader reader, Class<T> tClass)
    {
        final JsonObject innerObject = new JsonParser().parse(reader).getAsJsonObject().getAsJsonObject("d");
        return gson.fromJson(innerObject, tClass);
    }
}
