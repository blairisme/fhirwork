/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.common.network.Rest;

import com.mashape.unirest.http.HttpResponse;
import org.ucl.fhirwork.common.serialization.Serializer;

/**
 * Instances of this class represent a response from a REST service call.
 * Methods exist to obtain whether the request was successful and where
 * applicable object returned by the REST service.
 *
 * @author Blair Butterworth
 */
public class RestResponse
{
    private Serializer serializer;
    private HttpResponse<String> response;

    RestResponse(HttpResponse<String> response, Serializer serializer)
    {
        this.response = response;
        this.serializer = serializer;
    }

    public int getStatusCode()
    {
        return response.getStatus();
    }

    public boolean isEmpty()
    {
        String body = response.getBody();
        return body == null || body.isEmpty();
    }

    public String asString()
    {
        return response.getBody();
    }

    public <T> T asType(Class<T> type)
    {
        return serializer.deserialize(response.getBody(), type);
    }
}
