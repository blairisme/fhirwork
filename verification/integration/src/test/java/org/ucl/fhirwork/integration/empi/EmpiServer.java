/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.empi;

import static org.ucl.fhirwork.integration.empi.EmpiEndpoint.*;
import static org.ucl.fhirwork.integration.empi.EmpiHeader.*;
import static org.ucl.fhirwork.integration.empi.EmpiParameter.*;
import static org.ucl.fhirwork.integration.common.http.HttpHeader.*;
import static org.ucl.fhirwork.integration.common.http.MimeType.*;

import com.google.common.collect.ImmutableMap;
import org.ucl.fhirwork.integration.common.http.HttpUtils;
import org.ucl.fhirwork.integration.common.http.RestServer;
import org.ucl.fhirwork.integration.common.http.RestServerException;
import org.ucl.fhirwork.integration.empi.model.AuthenticationRequest;
import org.ucl.fhirwork.integration.empi.model.People;
import org.ucl.fhirwork.integration.empi.model.Person;
import org.ucl.fhirwork.integration.common.serialization.XmlSerializer;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class EmpiServer
{
    private RestServer server;
    private String username;
    private String password;
    private String address;

    public EmpiServer(String address, String username, String password)
    {
        this.address = address;
        this.username = username;
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public String getPingAddress() {
        return HttpUtils.combineUrl(address, Authenticate.getPath());
    }

    public void addPerson(Person person) throws RestServerException
    {
        RestServer server = getServer();
        server.put(AddPerson, person, Person.class);
    }

    public void removePeople() throws RestServerException
    {
        for (Person person: getPeople()){
            removePerson(person);
        }
    }

    public void removePerson(Person person) throws RestServerException
    {
        RestServer server = getServer();
        server.post(RemovePerson, ImmutableMap.of(PersonId, person.getPersonId()));
    }

    public List<Person> getPeople() throws RestServerException
    {
        int index = 0;
        int count = 100;

        Person[] people;
        List<Person> result = new ArrayList<>();

        while ((people = getPeople(index, count)).length > 0){
            index += count;
            for (Person person: people){
                result.add(person);
            }
        }
        return result;
    }

    public Person[] getPeople(int index, int count) throws RestServerException
    {
        RestServer server = getServer();
        People people = server.get(LoadAllPersons, People.class, ImmutableMap.of(FirstRecord, index, MaxRecords, count));
        return people.getPerson();
    }

    private RestServer getServer() throws RestServerException
    {
        if (server == null) {
            String token = getSessionToken();
            server = new RestServer(address, new XmlSerializer(), ImmutableMap.of(ContentType, Xml, SessionKey, token));
        }
        return server;
    }

    private String getSessionToken() throws RestServerException
    {
        AuthenticationRequest authentication = new AuthenticationRequest(username, password);
        RestServer server = new RestServer(address, new XmlSerializer(), ImmutableMap.of(ContentType, Xml));
        return server.put(Authenticate, authentication, AuthenticationRequest.class);
    }
}
