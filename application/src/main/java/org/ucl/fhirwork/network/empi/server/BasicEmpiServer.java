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
import org.ucl.fhirwork.common.network.exception.AmbiguousResultException;
import org.ucl.fhirwork.common.network.exception.ResourceExistsException;
import org.ucl.fhirwork.common.network.exception.ResourceMissingException;
import org.ucl.fhirwork.common.serialization.XmlSerializer;
import org.ucl.fhirwork.network.empi.data.InternalIdentifier;
import org.ucl.fhirwork.network.empi.data.People;
import org.ucl.fhirwork.network.empi.data.Person;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Arrays;
import java.util.List;

import static org.ucl.fhirwork.common.network.Rest.RestStatusStrategies.throwOnFailure;
import static org.ucl.fhirwork.common.network.Rest.RestStatusStrategies.throwOnFailureExcept;
import static org.ucl.fhirwork.common.network.http.HttpHeader.ContentType;
import static org.ucl.fhirwork.common.network.http.MimeType.Xml;
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
    private Provider<AuthenticatedRestServer> serverFactory;
    private AuthenticatedRestServer server;
    private String username;
    private String password;
    private String address;

    @Inject
    BasicEmpiServer(Provider<AuthenticatedRestServer> serverFactory){
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
        request.setStatusStrategy(throwOnFailure());

        RestResponse response = request.make();
        if (response.getStatusCode() == 204 || response.isEmpty()) {
            throw new ResourceExistsException("Person", person.getPersonId());
        }
        return response.asType(Person.class);
    }

    @Override
    public Person findPerson(Person template) throws RestException, ResourceMissingException, AmbiguousResultException
    {
        List<Person> people = findPersons(template);

        if (people.isEmpty()) {
            throw new ResourceMissingException("Person", template.toString());
        }
        if (people.size() > 1) {
            throw new AmbiguousResultException("Person", template.toString());
        }
        return people.get(0);
    }

    @Override
    public List<Person> findPersons(Person template) throws RestException
    {
        RestRequest request = getServer().post(FindPersons);
        request.setBody(template, Person.class);
        request.setStatusStrategy(throwOnFailure());

        RestResponse response = request.make();
        People people = response.asType(People.class);

        return Arrays.asList(people.getPerson());
    }

    @Override
    public Person loadPerson(InternalIdentifier identifier) throws RestException, ResourceMissingException
    {
        RestRequest request = getServer().get(LoadPerson);
        request.setParameters(ImmutableMap.of(PersonId, identifier.getValue()));
        request.setStatusStrategy(throwOnFailure());

        RestResponse response = request.make();
        if (response.getStatusCode() == 204 || response.isEmpty()) {
            throw new ResourceMissingException("Person", identifier.getValue());
        }
        return response.asType(Person.class);
    }

    @Override
    public List<Person> loadAllPersons(int index, int count) throws RestException
    {
        RestRequest request = getServer().get(LoadAllPersons);
        request.setParameters(ImmutableMap.of(FirstRecord, index, MaxRecords, count));
        request.setStatusStrategy(throwOnFailure());

        RestResponse response = request.make();
        People people = response.asType(People.class);

        return Arrays.asList(people.getPerson());
    }

    @Override
    public void removePerson(InternalIdentifier identifier) throws RestException
    {
        RestRequest request = getServer().post(RemovePerson);
        request.setParameters(ImmutableMap.of(PersonId, identifier.getValue()));
        request.setStatusStrategy(throwOnFailure());
        request.make();
    }

    @Override
    public Person updatePerson(Person person) throws RestException
    {
        RestRequest request = getServer().put(UpdatePerson);
        request.setBody(person, Person.class);
        request.setStatusStrategy(throwOnFailureExcept(304));

        RestResponse response = request.make();
        if (response.getStatusCode() == 304) {
            return person;
        }
        return response.asType(Person.class);
    }

    private synchronized RestServer getServer()
    {
        if (server == null) {
            server = serverFactory.get();
            server.setAddress(address);
            server.setSerializer(new XmlSerializer());
            server.setHeaders(ImmutableMap.of(ContentType, Xml));
            server.setAuthenticationStrategy(new EmpiAuthenticator(username, password));
        }
        return server;
    }
}
