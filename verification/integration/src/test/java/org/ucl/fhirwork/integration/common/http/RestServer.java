/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.common.http;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.ucl.fhirwork.integration.common.lang.StringCovertable;
import org.ucl.fhirwork.integration.common.serialization.Serializer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RestServer
{
    private String server;
    private Serializer serializer;
    private Map<String, String> headers;

    public RestServer(String address, Serializer serializer, Map<Object, Object> headers)
    {
        this.server = address.endsWith("/") ? address : address + "/";
        this.serializer = serializer;
        this.headers = convertHeaders(headers);
    }

    public void setHeader(Object key, Object value)
    {
        headers.put(convert(key), convert(value));
    }

    public void removeHeader(Object key)
    {
        headers.remove(convert(key));
    }

    public void delete(RestEndpoint endPoint, Map<Object, Object> parameters) throws RestServerException
    {
        delete(endPoint.getPath(), parameters);
    }

    public void delete(String endPoint, Map<Object, Object> parameters) throws RestServerException
    {
        try {
            HttpRequest request = Unirest.delete(server + endPoint)
                    .headers(headers)
                    .queryString(convertParameters(parameters));
            HttpResponse<String> response = request.asString();

            if (! isSuccessful(response.getStatus())) {
                throw new RestServerException(response.getStatus());
            }
        }
        catch (UnirestException exception){
            throw new RestServerException(exception);
        }
    }

    public <T> T get(RestEndpoint endPoint, Class<T> type, Map<Object, Object> parameters) throws RestServerException
    {
        return get(endPoint.getPath(), type, parameters);
    }

    public <T> T get(String path, Class<T> type, Map<Object, Object> parameters) throws RestServerException
    {
        try {
            HttpRequest request = Unirest.get(server + path)
                    .headers(headers)
                    .queryString(convertParameters(parameters));
            HttpResponse<String> response = request.asString();

            if (! isSuccessful(response.getStatus())) {
                throw new RestServerException(response.getStatus());
            }
            return serializer.deserialize(response.getBody(), type);
        }
        catch (UnirestException exception){
            throw new RestServerException(exception);
        }
    }

    public void get(RestEndpoint endPoint, Map<Object, Object> parameters) throws RestServerException
    {
        get(endPoint.getPath(), parameters);
    }

    public void get(String path, Map<Object, Object> parameters) throws RestServerException
    {
        try {
            HttpRequest request = Unirest.get(server + path)
                    .headers(headers)
                    .queryString(convertParameters(parameters));
            HttpResponse<String> response = request.asString();

            if (! isSuccessful(response.getStatus())) {
                throw new RestServerException(response.getStatus());
            }
        }
        catch (UnirestException exception){
            throw new RestServerException(exception);
        }
    }

    public boolean testGet(RestEndpoint endPoint, Map<Object, Object> parameters, int status) throws RestServerException
    {
        try {
            HttpRequest request = Unirest.get(server + endPoint.getPath())
                    .headers(headers)
                    .queryString(convertParameters(parameters));
            HttpResponse<String> response = request.asString();
            return response.getStatus() == status;
        }
        catch (UnirestException exception){
            throw new RestServerException(exception);
        }
    }

    public <T> void post(RestEndpoint endPoint, T value, Class<T> type, Map<Object, Object> parameters) throws RestServerException
    {
        try {
            RequestBodyEntity request = Unirest.post(server + endPoint.getPath())
                    .headers(headers)
                    .queryString(convertParameters(parameters))
                    .body(serializer.serialize(value, type));
            HttpResponse<String> response = request.asString();

            if (! isSuccessful(response.getStatus())) {
                throw new RestServerException(response.getStatus());
            }
        }
        catch (UnirestException exception){
            throw new RestServerException(exception);
        }
    }

    public void post(RestEndpoint endPoint, String content, Map<Object, Object> parameters) throws RestServerException
    {
        try {
            RequestBodyEntity request = Unirest.post(server + endPoint.getPath())
                    .headers(headers)
                    .queryString(convertParameters(parameters))
                    .body(content);
            HttpResponse<String> response = request.asString();

            if (! isSuccessful(response.getStatus())) {
                throw new RestServerException(response.getStatus());
            }
        }
        catch (UnirestException exception){
            throw new RestServerException(exception);
        }
    }

    public <T> T post(RestEndpoint endPoint, Class<T> returnType, Map<Object, Object> parameters) throws RestServerException
    {
        try {
            HttpRequestWithBody request = Unirest.post(server + endPoint.getPath())
                    .headers(headers)
                    .queryString(convertParameters(parameters));
            HttpResponse<String> response = request.asString();

            if (! isSuccessful(response.getStatus())) {
                throw new RestServerException(response.getStatus());
            }
            return serializer.deserialize(response.getBody(), returnType);
        }
        catch (UnirestException exception){
            throw new RestServerException(exception);
        }
    }

    public String post(RestEndpoint endPoint, Map<Object, Object> parameters) throws RestServerException
    {
        try {
            HttpRequestWithBody request = Unirest.post(server + endPoint.getPath())
                    .headers(headers)
                    .queryString(convertParameters(parameters));
            HttpResponse<String> response = request.asString();

            if (! isSuccessful(response.getStatus())) {
                throw new RestServerException(response.getStatus());
            }
            return response.getBody();
        }
        catch (UnirestException exception){
            throw new RestServerException(exception);
        }
    }

    public <T> String put(RestEndpoint endPoint, T value, Class<T> type) throws RestServerException {
        return put(endPoint.getPath(), value, type, Collections.emptyMap());
    }

    public <T> String put(String path, T value, Class<T> type, Map<Object, Object> parameters) throws RestServerException
    {
        try {
            String body = serializer.serialize(value, type);
            RequestBodyEntity request = Unirest.put(server + path)
                    .headers(headers)
                    .queryString(convertParameters(parameters))
                    .body(body);
            HttpResponse<String> response = request.asString();

            if (! HttpStatus.isSuccessful(response.getStatus())) {
                throw new RestServerException(response.getStatus());
            }
            return response.getBody();
        }
        catch (UnirestException exception){
            throw new RestServerException(exception);
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
        if (object instanceof StringCovertable){
            StringCovertable convertable = (StringCovertable)object;
            return convertable.toString();
        }
        if (object instanceof String){
            return (String)object;
        }
        return object.toString();
    }
}
