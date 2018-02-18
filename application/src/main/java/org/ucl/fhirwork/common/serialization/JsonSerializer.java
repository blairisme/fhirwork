/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.common.serialization;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * Instances of this class serialize objects into their equivalent JSON
 * representation. Methods are provided to convert Java objects into JSON
 * and JSON into Java objects.
 *
 * @author Blair Butterworth
 */
public class JsonSerializer implements Serializer
{
    private Gson gson;

    public JsonSerializer()
    {
        gson = new Gson();
    }

    @Override
    public <T> String serialize(T value, Class<T> type) throws SerializationException
    {
        return gson.toJson(value, type);
    }

    @Override
    public <T> void serialize(T value, Class<T> type, Writer writer) throws SerializationException
    {
        gson.toJson(value, type, writer);
    }

    @Override
    public <T> T deserialize(String value, Class<T> type) throws SerializationException
    {
        return gson.fromJson(value, type);
    }

    @Override
    public <T> T deserialize(Reader reader, Class<T> type) throws SerializationException
    {
        return gson.fromJson(reader, type);
    }

}
