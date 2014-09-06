package com.jivesoftware.boundaries.restz;

import com.jivesoftware.boundaries.restz.strategies.SmartReadingStrategy;
import com.jivesoftware.boundaries.restz.strategies.SerializerReadingStrategy;
import com.jivesoftware.boundaries.serializing.GsonSerializer;

import java.io.IOException;

/**
 * Created by bmoshe on 4/2/14.
 */
public interface ResponseReadingStrategy
{
    <T> T read(Response response, Class<T> tClass) throws IOException;

    static final ResponseReadingStrategy gsonResponseReadingStrategy = new SerializerReadingStrategy(new GsonSerializer());
    static final ResponseReadingStrategy smartResponseReadingStrategy = new SmartReadingStrategy(gsonResponseReadingStrategy);
}
