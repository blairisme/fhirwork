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

public class JsonSerializer implements Serializer
{
    private Gson gson;

    public JsonSerializer()
    {
        gson = new Gson();
    }

    public <T> String serialize(T value, Class<T> type)
    {
        return gson.toJson(value, type);
    }

    public <T> T deserialize(String value, Class<T> type)
    {
        return gson.fromJson(value, type);
    }
}
