/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.common.serialization;

import com.google.gson.Gson;

public class JsonSerializer
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
