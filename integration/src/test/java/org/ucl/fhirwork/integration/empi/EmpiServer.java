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
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.ucl.fhirwork.integration.common.http.HttpStatus;
import org.ucl.fhirwork.integration.empi.model.AuthenticationRequest;
import org.ucl.fhirwork.integration.empi.model.People;
import org.ucl.fhirwork.integration.empi.model.Person;
import org.ucl.fhirwork.integration.common.serialization.XmlSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpiServer
{
    private static final String CONTENT_TYPE_XML = "application/xml";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String SESSION_KEY_HEADER = "OPENEMPI_SESSION_KEY";

    private static final String FIRST_RECORD_PARAMETER = "firstRecord";
    private static final String MAX_RECORDS_PARAMETER = "maxRecords";
    private static final String PERSON_ID_PARAMETER = "personId";

    private static final String AUTHENTICATE_ENDPOINT = "openempi-admin/openempi-ws-rest/security-resource/authenticate";
    private static final String ADD_PERSON_ENDPOINT = "openempi-admin/openempi-ws-rest/person-manager-resource/addPerson";
    private static final String REMOVE_PERSON_ENDPOINT = "openempi-admin/openempi-ws-rest/person-manager-resource/removePersonById";
    private static final String LIST_PERSONS_ENDPOINT = "openempi-admin/openempi-ws-rest/person-query-resource/loadAllPersonsPaged";

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

    public void addPerson(Person person) throws EmpiServerException
    {
        put(ADD_PERSON_ENDPOINT, person, Person.class);
    }

    public void removeAllPatients() throws EmpiServerException
    {
        List<Person> patients = getPatients();
        for (Person patient: patients){
            removePatient(patient);
        }
    }

    public void removePatient(Person person) throws EmpiServerException
    {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(PERSON_ID_PARAMETER, person.getPersonId());
        post(REMOVE_PERSON_ENDPOINT, parameters);
    }

    public List<Person> getPatients() throws EmpiServerException
    {
        int index = 0;
        int count = 100;
        Person[] persons;
        List<Person> result = new ArrayList<>();

        while ((persons = getPatients(index, count)).length > 0){
            index += count;
            for (Person person: persons){
                result.add(person);
            }
        }
        return result;
    }

    private Person[] getPatients(int index, int count) throws EmpiServerException
    {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(FIRST_RECORD_PARAMETER, index);
        parameters.put(MAX_RECORDS_PARAMETER, count);

        People people = get(LIST_PERSONS_ENDPOINT, People.class, parameters);
        return people.getPerson();
    }

    private <T> T get(String endPoint, Class<T> type, Map<String, Object> parameters) throws EmpiServerException
    {
        String sessionKey = getSessionToken();

        try {
            HttpRequest request = Unirest.get(server + endPoint)
                    .header(SESSION_KEY_HEADER, sessionKey)
                    .header(CONTENT_TYPE_HEADER, CONTENT_TYPE_XML)
                    .queryString(parameters);
            HttpResponse<String> response = request.asString();

            if (! HttpStatus.isSuccessful(response.getStatus())) {
                throw new EmpiServerException(response.getStatus());
            }
            return serializer.deserialize(response.getBody(), type);
        }
        catch (UnirestException exception){
            throw new EmpiServerException(exception);
        }
    }

    private void post(String endPoint, Map<String, Object> parameters) throws EmpiServerException
    {
        String sessionKey = getSessionToken();

        try {
            HttpRequestWithBody request = Unirest.post(server + endPoint)
                    .header(SESSION_KEY_HEADER, sessionKey)
                    .header(CONTENT_TYPE_HEADER, CONTENT_TYPE_XML)
                    .queryString(parameters);
            HttpResponse<String> response = request.asString();

            if (! HttpStatus.isSuccessful(response.getStatus())) {
                throw new EmpiServerException(response.getStatus());
            }
        }
        catch (UnirestException exception){
            throw new EmpiServerException(exception);
        }
    }

    private <T> void put(String endPoint, T value, Class<T> type) throws EmpiServerException
    {
        String sessionKey = getSessionToken();
        String requestBody = serializer.serialize(value, type);

        try {
            RequestBodyEntity request = Unirest.put(server + endPoint)
                    .header(SESSION_KEY_HEADER, sessionKey)
                    .header(CONTENT_TYPE_HEADER, CONTENT_TYPE_XML)
                    .body(requestBody);
            HttpResponse<String> response = request.asString();

            if (! HttpStatus.isSuccessful(response.getStatus())) {
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

            if (! HttpStatus.isSuccessful(response.getStatus())) {
                throw new EmpiServerException(response.getStatus());
            }
            return response.getBody();
        }
        catch (UnirestException exception){
            throw new EmpiServerException(exception);
        }
    }
}
