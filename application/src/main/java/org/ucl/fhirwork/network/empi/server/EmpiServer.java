/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.empi.server;

import static org.ucl.fhirwork.network.empi.server.EmpiHeader.SessionKey;
import static org.ucl.fhirwork.common.http.HttpHeader.ContentType;
import static org.ucl.fhirwork.common.http.MimeType.Xml;
import static org.ucl.fhirwork.network.empi.server.EmpiParameter.PersonId;
import static org.ucl.fhirwork.network.empi.server.EmpiResource.*;

import com.google.common.collect.ImmutableMap;
import org.ucl.fhirwork.common.http.*;
import org.ucl.fhirwork.common.serialization.XmlSerializer;
import org.ucl.fhirwork.network.empi.data.AuthenticationRequest;
import org.ucl.fhirwork.network.empi.data.People;
import org.ucl.fhirwork.network.empi.data.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Instances of this class represent an EMPI server. Methods exists to create,
 * read, update and delete patient data.
 *
 * @author Blair Butterworth
 */
public class EmpiServer
{
    private RestServer server;
    private String username;
    private String password;
    private String address;

    public EmpiServer()
    {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Person addPerson(Person person) throws RestException
    {
        RestRequest request = getServer().put(AddPerson);
        request.setBody(person, Person.class);

        RestResponse response = request.make(HandleFailure.ByException);
        return response.asType(Person.class);
    }

    /**
     * This method returns a {@link List} of {@link Person} records that match
     * any of the person attributes that are provided in the search {@code
     * Person} object which acts as a template.
     *
     * @return  a collection of people matching the given {@code Person}
     *          template.
     */
    public List<Person> findPersonsByAttributes(Person person) throws RestException
    {
        RestRequest request = getServer().post(FindPersonsByAttributes);
        request.setBody(person, Person.class);

        RestResponse response = request.make(HandleFailure.ByException);
        People people = response.asType(People.class);

        return Arrays.asList(people.getPerson());
    }

    //TODO: Handle code 204 operation success but person missing
    public Person loadPerson(String personId) throws RestException
    {
        RestRequest request = getServer().get(LoadPerson);
        request.setParameters(ImmutableMap.of(PersonId, personId));

        RestResponse response = request.make(HandleFailure.ByException);
        return response.asType(Person.class);
    }

    public boolean personExists(String personId) throws RestException
    {
        RestRequest request = getServer().get(LoadPerson);
        request.setParameters(ImmutableMap.of(PersonId, personId));

        RestResponse response = request.make(HandleFailure.ByException);
        return response.getStatusCode() == 204;
    }

    public void removePerson(String personId) throws RestException
    {
        RestRequest request = getServer().post(RemovePerson);
        request.setParameters(ImmutableMap.of(PersonId, personId));
        request.make(HandleFailure.ByException);
    }

    public Person updatePerson(Person person) throws RestException
    {
        RestRequest request = getServer().put(UpdatePerson);
        request.setBody(person, Person.class);

        RestResponse response = request.make(HandleFailure.ByException);
        return response.asType(Person.class);
    }

    private RestServer getServer() throws RestException
    {
        if (server == null) {
            String token = getSessionToken();
            server = new RestServer(address, new XmlSerializer(), ImmutableMap.of(ContentType, Xml, SessionKey, token));
        }
        return server;
    }

    private String getSessionToken() throws RestException
    {
        AuthenticationRequest authentication = new AuthenticationRequest(username, password);
        RestServer server = new RestServer(address, new XmlSerializer(), ImmutableMap.of(ContentType, Xml));

        RestRequest request = server.put(Authenticate);
        request.setBody(authentication, AuthenticationRequest.class);

        RestResponse response = request.make(HandleFailure.ByException);
        return response.asString();
    }
}
