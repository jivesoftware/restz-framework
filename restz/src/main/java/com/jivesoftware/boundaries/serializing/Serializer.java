package com.jivesoftware.boundaries.serializing;

import java.io.Reader;
import java.io.Writer;

/**
 * Created by bmoshe on 8/25/14.
 */
public interface Serializer
{
    String serialize(Object obj);
    void serialize(Object obj, Writer writer);

    <T> T deserialize(String serializedObj, Class<T> tClass);
    <T> T deserialize(Reader reader, Class<T> tClass);
}
