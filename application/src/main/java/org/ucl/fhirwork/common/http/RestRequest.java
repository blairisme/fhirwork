/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.common.http;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.ucl.fhirwork.common.serialization.Serializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Instances of this class represent a request to a REST service. Methods exist
 * to set the query parameters and body of the request as well as to make the
 * request itself.
 *
 * @author Blair Butterworth
 */
public class RestRequest
{
    private Serializer serializer;
    private HttpRequestWithBody request;

    RestRequest(HttpRequestWithBody request, Serializer serializer)
    {
        this.request = request;
        this.serializer = serializer;
    }

    public RestRequest setParameters(Map<Object, Object> parameters)
    {
        request.queryString(convert(parameters));
        return this;
    }

    public <T> RestRequest setBody(T value, Class<T> type)
    {
        request.body(serializer.serialize(value, type));
        return this;
    }

    public RestResponse make(HandleFailure mode) throws RestException
    {
        try
        {
            HttpResponse<String> response = request.asString();
            RestResponse result = new RestResponse(response, serializer);

            if (! result.wasSuccessful() && mode == HandleFailure.ByException){
                throw new RestException(result.getStatusCode());
            }
            return result;
        }
        catch (UnirestException exception){
            throw new RestException(exception);
        }
    }

    private Map<String, Object> convert(Map<Object, Object> values)
    {
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<Object, Object> entry: values.entrySet()){
            result.put(convert(entry.getKey()), convert(entry.getValue()));
        }
        return result;
    }

    private String convert(Object object)
    {
        return object.toString();
    }
}
