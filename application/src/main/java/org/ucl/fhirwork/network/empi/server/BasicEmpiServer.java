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

import com.google.common.collect.ImmutableMap;
import org.ucl.fhirwork.common.network.Rest.*;
import org.ucl.fhirwork.common.network.exception.*;
import org.ucl.fhirwork.common.serialization.Serializer;
import org.ucl.fhirwork.common.serialization.XmlSerializer;
import org.ucl.fhirwork.network.empi.data.AuthenticationRequest;
import org.ucl.fhirwork.network.empi.data.People;
import org.ucl.fhirwork.network.empi.data.Person;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.ucl.fhirwork.common.network.Rest.RestStatusHandlers.throwOnFailedStatus;
import static org.ucl.fhirwork.common.network.Rest.RestStatusHandlers.throwOnFailureExcept;
import static org.ucl.fhirwork.common.network.http.HttpHeader.ContentType;
import static org.ucl.fhirwork.common.network.http.MimeType.Xml;
import static org.ucl.fhirwork.network.empi.server.EmpiHeader.SessionKey;
import static org.ucl.fhirwork.network.empi.server.EmpiParameter.*;
import static org.ucl.fhirwork.network.empi.server.EmpiResource.*;

/**
 * Instances of this class represent an EMPI server. Methods exists to create,
 * read, update and delete patient data.
 *
 * @author Blair Butterworth
 */
public class BasicEmpiServer implements EmpiServer
{
    private Provider<RestServer> serverFactory;
    private RestServer server;
    private String username;
    private String password;
    private String address;

    @Inject
    BasicEmpiServer(Provider<RestServer> serverFactory){
        this.serverFactory = serverFactory;
    }

    @Override
    public synchronized void setConnectionDetails(String address, String username, String password) {
        this.address = address;
        this.username = username;
        this.password = password;
        this.server = null;
    }

    @Override
    public Person addPerson(Person person) throws RestException
    {
        RestRequest request = getServer().put(AddPerson);
        request.setBody(person, Person.class);

        RestResponse response = request.make(throwOnFailedStatus());
        if (response.getStatusCode() == 204 || response.isEmpty()) {
            throw new ResourceExistsException("Person", person.getPersonId());
        }
        return response.asType(Person.class);
    }

    @Override
    public Person findPerson(Person template) throws RestException, ResourceMissingException, AmbiguousResultException
    {
        List<Person> people = findPersons(template);

        if (people.isEmpty()){
            throw new ResourceMissingException("Person", template.toString());
        }
        if (people.size() > 1){
            throw new AmbiguousResultException("Person", template.toString());
        }
        return people.get(0);
    }

    @Override
    public List<Person> findPersons(Person template) throws RestException
    {
        RestRequest request = getServer().post(FindPersons);
        request.setBody(template, Person.class);

        RestResponse response = request.make(throwOnFailedStatus());
        People people = response.asType(People.class);

        return Arrays.asList(people.getPerson());
    }

    @Override
    public Person loadPerson(String personId) throws RestException, ResourceMissingException
    {
        RestRequest request = getServer().get(LoadPerson);
        request.setParameters(ImmutableMap.of(PersonId, personId));

        RestResponse response = request.make(throwOnFailedStatus());
        if (response.getStatusCode() == 204 || response.isEmpty()) {
            throw new ResourceMissingException("Person", personId);
        }
        return response.asType(Person.class);
    }

    @Override
    public List<Person> loadAllPersons(int index, int count) throws RestException
    {
        RestRequest request = getServer().get(LoadAllPersons);
        request.setParameters(ImmutableMap.of(FirstRecord, index, MaxRecords, count));

        RestResponse response = request.make(throwOnFailedStatus());
        People people = response.asType(People.class);

        return Arrays.asList(people.getPerson());
    }

    @Override
    public void removePerson(String personId) throws RestException
    {
        RestRequest request = getServer().post(RemovePerson);
        request.setParameters(ImmutableMap.of(PersonId, personId));
        request.make(throwOnFailedStatus());
    }

    @Override
    public Person updatePerson(Person person) throws RestException
    {
        RestRequest request = getServer().put(UpdatePerson);
        request.setBody(person, Person.class);

        RestResponse response = request.make(throwOnFailureExcept(304));
        if (response.getStatusCode() == 304){
            return person;
        }
        return response.asType(Person.class);
    }

    private synchronized RestServer getServer() throws RestException
    {
        if (server == null) {
            String token = getSessionToken();
            server = newServer(address, new XmlSerializer(), ImmutableMap.of(ContentType, Xml, SessionKey, token));
        }
        return server;
    }

    private String getSessionToken() throws RestException
    {
        AuthenticationRequest authentication = new AuthenticationRequest(username, password);
        RestServer server = newServer(address, new XmlSerializer(), ImmutableMap.of(ContentType, Xml));

        RestRequest request = server.put(Authenticate);
        request.setBody(authentication, AuthenticationRequest.class);
        RestResponse response = request.make(throwOnFailureExcept(401));

        if (response.getStatusCode() == 401) {
            throw new AuthenticationException(address, username);
        }
        return response.asString();
    }

    private RestServer newServer(String address, Serializer serializer, Map<Object, Object> headers)
    {
        RestServer result = serverFactory.get();
        result.setAddress(address);
        result.setSerializer(serializer);
        result.setHeaders(headers);
        return result;
    }
}
