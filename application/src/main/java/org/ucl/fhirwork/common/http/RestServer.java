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

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.ucl.fhirwork.common.serialization.Serializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Instances of this class represent a representational state transfer (REST)
 * web service.
 *
 * @author Blair Butterworth
 */
public class RestServer
{
    private String server;
    private Serializer serializer;
    private Map<String, String> headers;

    public RestServer() {
    }

    public void setAddress(String address) {
        this.server = address.endsWith("/") ? address : address + "/"; // TODO: (blair) Use URL/URI class instead.
    }

    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    public void setHeaders(Map<Object, Object> values) {
        this.headers = convertHeaders(values);
    }

    public RestRequest get(RestResource resource){
        return get(resource.getPath());
    }

    public RestRequest get(String path){
        GetRequest request = Unirest.get(server + path).headers(headers);
        return new RestRequest(request, serializer);
    }

    public RestRequest post(RestResource resource)
    {
        return post(resource.getPath());
    }

    public RestRequest post(String path) {
        HttpRequestWithBody request = Unirest.post(server + path).headers(headers);
        return new RestRequest(request, serializer);
    }

    public RestRequest put(RestResource resource) {
        return put(resource.getPath());
    }

    public RestRequest put(String path) {
        HttpRequestWithBody request = Unirest.put(server + path).headers(headers);
        return new RestRequest(request, serializer);
    }

    public RestRequest delete(RestResource resource) {
        return delete(resource.getPath());
    }

    public RestRequest delete(String path) {
        HttpRequest request = Unirest.delete(server + path).headers(headers);
        return new RestRequest(request, serializer);
    }

    private Map<String, String> convertHeaders(Map<Object, Object> values) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<Object, Object> entry: values.entrySet()){
            result.put(convert(entry.getKey()), convert(entry.getValue()));
        }
        return result;
    }

    private String convert(Object object) {
        return object.toString();
    }
}
