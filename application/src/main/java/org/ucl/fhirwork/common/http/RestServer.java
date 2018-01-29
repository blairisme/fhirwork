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
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.ucl.fhirwork.common.serialization.Serializer;

import java.util.HashMap;
import java.util.Map;

public class RestServer
{
    private String server;
    private Serializer serializer;
    private Map<String, String> headers;

    public RestServer(String address, Serializer serializer, Map<Object, Object> headers)
    {
        this.server = address.endsWith("/") ? address : address + "/"; // (blair) Use URL/URI class instead. This is fragile
        this.serializer = serializer;
        this.headers = convertHeaders(headers);
    }

    public <T> String put(RestEndpoint endPoint, T value, Class<T> type) throws RestException {
        return put(endPoint.getPath(), value, type);
    }

    public <T> String put(String path, T value, Class<T> type) throws RestException {
        try {
            String body = serializer.serialize(value, type);
            RequestBodyEntity request = Unirest.put(server + path)
                    .headers(headers)
                    .body(body);
            HttpResponse<String> response = request.asString();

            if (! isSuccessful(response.getStatus())) {
                throw new RestException(response.getStatus());
            }
            return response.getBody();
        }
        catch (UnirestException exception){
            throw new RestException(exception);
        }
    }

    private boolean isSuccessful(int code)
    {
        return (code >= 200 && code <= 299);
    }

    private Map<String, String> convertHeaders(Map<Object, Object> values)
    {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<Object, Object> entry: values.entrySet()){
            result.put(convert(entry.getKey()), convert(entry.getValue()));
        }
        return result;
    }

    private Map<String, Object> convertParameters(Map<Object, Object> values)
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
