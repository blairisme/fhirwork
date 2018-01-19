/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.empi;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.ucl.fhirwork.integration.data.Patient;
import org.ucl.fhirwork.integration.empi.model.AuthenticationRequest;
import org.ucl.fhirwork.integration.empi.model.Person;
import org.ucl.fhirwork.integration.serialization.XmlSerializer;

public class EmpiServer
{
    private static final String CONTENT_TYPE_XML = "application/xml";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String SESSION_KEY_HEADER = "OPENEMPI_SESSION_KEY";

    private static final String AUTHENTICATE_ENDPOINT = "openempi-admin/openempi-ws-rest/security-resource/authenticate";
    private static final String ADD_PERSON_ENDPOINT = "openempi-admin/openempi-ws-rest/person-manager-resource/addPerson";

    private String server;
    private String username;
    private String password;
    private String sessionToken;
    private XmlSerializer serializer;

    public EmpiServer(String server, String username, String password)
    {
        this.server = server.endsWith("/") ? server : server + "/";
        this.username = username;
        this.password = password;
        this.sessionToken = null;
        this.serializer = new XmlSerializer();
    }

    public void addPatient(Patient patient) throws EmpiServerException
    {
        Person person = new Person(patient);
        post(ADD_PERSON_ENDPOINT, person, Person.class);
    }

    private <T> void post(String endPoint, T value, Class<T> type) throws EmpiServerException
    {
        String sessionKey = getSessionToken();
        String requestBody = serializer.serialize(value, type);

        try {
            RequestBodyEntity request = Unirest.put(server + endPoint)
                    .header(SESSION_KEY_HEADER, sessionKey)
                    .header(CONTENT_TYPE_HEADER, CONTENT_TYPE_XML)
                    .body(requestBody);
            HttpResponse<String> response = request.asString();

            if (response.getStatus() != 200){
                throw new EmpiServerException(response.getStatus());
            }
        }
        catch (UnirestException exception){
            throw new EmpiServerException(exception);
        }
    }

    private String getSessionToken() throws EmpiServerException
    {
        if (sessionToken == null){
            sessionToken = authenticate();
        }
        return sessionToken;
    }

    private String authenticate() throws EmpiServerException
    {
        try {
            AuthenticationRequest authentication = new AuthenticationRequest(username, password);
            String requestBody = serializer.serialize(authentication, AuthenticationRequest.class);

            RequestBodyEntity request = Unirest.put(server + AUTHENTICATE_ENDPOINT)
                    .header(CONTENT_TYPE_HEADER, CONTENT_TYPE_XML)
                    .body(requestBody);
            HttpResponse<String> response = request.asString();

            if (response.getStatus() != 200){
                throw new EmpiServerException(response.getStatus());
            }
            return response.getBody();
        }
        catch (UnirestException exception){
            throw new EmpiServerException(exception);
        }
    }
}
