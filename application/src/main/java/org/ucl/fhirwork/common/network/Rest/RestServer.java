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

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.ucl.fhirwork.common.serialization.Serializer;

import javax.inject.Inject;
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
    private String address;
    private Serializer serializer;
    private Map<String, String> headers;

    @Inject
    @SuppressWarnings("unused")
    public RestServer() {
    }

    public RestServer(RestServer another) {
        this.address = another.address;
        this.serializer = another.serializer;
        this.headers = another.headers;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getHeader(Object key) {
        return this.headers.get(convert(key));
    }

    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    public void setHeader(Object key, Object value) {
        this.headers.put(convert(key), convert(value));
    }

    public void setHeaders(Map<Object, Object> values) {
        this.headers = convertHeaders(values);
    }

    public RestRequest get(RestResource resource) {
        return get(resource.getPath());
    }

    public RestRequest get(String path) {
        String url = combine(address, path);
        GetRequest request = Unirest.get(url).headers(headers);
        return newRestRequest(request, serializer);
    }

    public RestRequest post(RestResource resource) {
        return post(resource.getPath());
    }

    public RestRequest post(String path) {
        String url = combine(address, path);
        HttpRequestWithBody request = Unirest.post(url).headers(headers);
        return newRestRequest(request, serializer);
    }

    public RestRequest put(RestResource resource) {
        return put(resource.getPath());
    }

    public RestRequest put(String path) {
        String url = combine(address, path);
        HttpRequestWithBody request = Unirest.put(url).headers(headers);
        return newRestRequest(request, serializer);
    }

    public RestRequest delete(RestResource resource) {
        return delete(resource.getPath());
    }

    public RestRequest delete(String path) {
        String url = combine(address, path);
        HttpRequest request = Unirest.delete(url).headers(headers);
        return newRestRequest(request, serializer);
    }

    protected Map<String, String> convertHeaders(Map<Object, Object> values) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<Object, Object> entry: values.entrySet()){
            result.put(convert(entry.getKey()), convert(entry.getValue()));
        }
        return result;
    }

    protected String convert(Object object) {
        return object != null ? object.toString() : null;
    }

    protected RestRequest newRestRequest(HttpRequest request, Serializer serializer) {
        return new RestRequest(request, serializer);
    }

    private String combine(String root, String path) {

        StringBuilder result = new StringBuilder();
        result.append(root);

        if (!root.endsWith("/") && !path.startsWith("/")){
            result.append("/");
        }
        result.append(path);
        return result.toString();
    }
}
