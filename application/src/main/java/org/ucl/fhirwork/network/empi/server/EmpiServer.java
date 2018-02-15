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
import static org.ucl.fhirwork.network.empi.server.EmpiParameter.FirstRecord;
import static org.ucl.fhirwork.network.empi.server.EmpiParameter.MaxRecords;
import static org.ucl.fhirwork.network.empi.server.EmpiParameter.PersonId;
import static org.ucl.fhirwork.network.empi.server.EmpiResource.*;

import com.google.common.collect.ImmutableMap;
import org.ucl.fhirwork.common.http.*;
import org.ucl.fhirwork.common.serialization.Serializer;
import org.ucl.fhirwork.common.serialization.XmlSerializer;
import org.ucl.fhirwork.network.empi.data.AuthenticationRequest;
import org.ucl.fhirwork.network.empi.data.Identifier;
import org.ucl.fhirwork.network.empi.data.People;
import org.ucl.fhirwork.network.empi.data.Person;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Instances of this class represent an EMPI server. Methods exists to create,
 * read, update and delete patient data.
 *
 * @author Blair Butterworth
 */
public class EmpiServer
{
    private Provider<RestServer> serverFactory;
    private RestServer server;
    private String username;
    private String password;
    private String address;

    @Inject
    public EmpiServer(Provider<RestServer> serverFactory){
        this.serverFactory = serverFactory;
    }

    /**
     * Sets the address and authentication information used to connect to the
     * EMPI server.
     *
     * @param address   the URL of an EMPI server.
     * @param username  the name of an account on the EMPI server.
     * @param password  the password of an EMPI account.
     */
    public synchronized void setConnectionDetails(String address, String username, String password) {
        this.address = address;
        this.username = username;
        this.password = password;
        this.server = null;
    }

    /**
     * This methods adds a {@link Person} to the EMPI system. The system will
     * first check to see if a {@code Person} with the same identifier is
     * already known to the system. If the {@code Person} is known already then
     * nothing further will be done.
     *
     * @param person            the {@ode Person} to add.
     * @return                  the {@ode Person} that was added.
     * @throws RestException    thrown if an error occurs whilst communicating
     *                          with the EMPI server.
     */
    public Person addPerson(Person person) throws RestException
    {
        RestRequest request = getServer().put(AddPerson);
        request.setBody(person, Person.class);

        RestResponse response = request.make(HandleFailure.ByException);
        return response.getStatusCode() != 204 ? response.asType(Person.class) : person;
    }

    /**
     * Returns the {@link Person} that matches the given {@link Identifier}.
     *
     * @param identifier        the {@code Identifier} of the desired
     *                          {@code Person}.
     * @return                  the matching {@code Person}.
     * @throws RestException    thrown if an error occurs whilst communicating
     *                          with the EMPI server.
     */
    //TODO: Handle missing person
    public Person findPersonById(Identifier identifier) throws RestException
    {
        RestRequest request = getServer().post(FindPersonById);
        request.setBody(identifier, Identifier.class);

        RestResponse response = request.make(HandleFailure.ByException);
        return response.asType(Person.class);
    }

    /**
     * This method returns a {@link List} of {@link Person} records that match
     * any of the person attributes that are provided in the search {@code
     * Person} object which acts as a template.
     *
     * @return                  a collection of people matching the given
     *                          {@code Person} template.
     * @throws RestException    thrown if an error occurs whilst communicating
     *                          with the EMPI server.
     */
    public List<Person> findPersonsByAttributes(Person person) throws RestException
    {
        RestRequest request = getServer().post(FindPersonsByAttributes);
        request.setBody(person, Person.class);

        RestResponse response = request.make(HandleFailure.ByException);
        People people = response.asType(People.class);

        return Arrays.asList(people.getPerson());
    }

    /**
     * Returns biographical information on the {@link Person} with the given
     * EMPI identifier (internal EMPI identifier)
     *
     * @param personId          the identifier of the {@code Person} to load.
     * @return                  a {@code Person} instance contain information
     *                          on the desired person.
     * @throws RestException    thrown if an error occurs whilst communicating
     *                          with the EMPI server.
     */
    //TODO: Handle code 204 operation success but person missing
    public Person loadPerson(String personId) throws RestException
    {
        RestRequest request = getServer().get(LoadPerson);
        request.setParameters(ImmutableMap.of(PersonId, personId));

        RestResponse response = request.make(HandleFailure.ByException);
        return response.asType(Person.class);
    }

    /**
     * Returns biographical information on all people in the EMPI system.
     *
     * @param index             the index to read people from.
     * @param count             the maximum number of people to read.
     * @return                  a list of people.
     * @throws RestException    thrown if an error occurs whilst communicating
     *                          with the EMPI server.
     */
    public List<Person> loadAllPersons(int index, int count) throws RestException
    {
        RestRequest request = getServer().get(LoadAllPersons);
        request.setParameters(ImmutableMap.of(FirstRecord, index, MaxRecords, count));

        RestResponse response = request.make(HandleFailure.ByException);
        People people = response.asType(People.class);

        return Arrays.asList(people.getPerson());
    }

    /**
     * Determines whether a person with the given identifier is stored in the
     * EMPI system.
     *
     * @param personId          the identifier of the person whose existence is
     *                          in question.
     * @return                  {@code true} if a person exists, otherwise
     *                          {@code false}.
     * @throws RestException    thrown if an error occurs whilst communicating
     *                          with the EMPI server.
     */
    public boolean personExists(String personId) throws RestException
    {
        RestRequest request = getServer().get(LoadPerson);
        request.setParameters(ImmutableMap.of(PersonId, personId));

        RestResponse response = request.make(HandleFailure.ByException);
        return response.getStatusCode() == 204;
    }

    /**
     * This method removes a {@@link Person} from the EMPI system. The system
     * locates the {@code Person} record using their internal unique id. If the
     * record is found, the record is removed from the system completely.
     *
     * @param personId          the identifier of the {@code Person} to remove.
     * @throws RestException    thrown if an error occurs whilst communicating
     *                          with the EMPI server.
     */
    public void removePerson(String personId) throws RestException
    {
        RestRequest request = getServer().post(RemovePerson);
        request.setParameters(ImmutableMap.of(PersonId, personId));
        request.make(HandleFailure.ByException);
    }

    /**
     * This method updates the attributes maintained in the EMPI system about
     * the given {@link Person}. The system will locate the {@code Person}
     * record using the internal person identifier. The attributes in the given
     * {@code Person} are used to update the {@code Person}'s record.
     *
     * @param person            the {@code Person} to updated.
     * @return                  the updated {@code Person}.
     * @throws RestException    thrown if an error occurs whilst communicating
     *                          with the EMPI server.
     */
    //TODO: Documentation suggests this method will throw if the person isnt found - evaluate if this is appropriate.
    //TODO: Returns 304 if the person isnt modified - evaluate if we should throw
    public Person updatePerson(Person person) throws RestException
    {
        RestRequest request = getServer().put(UpdatePerson);
        request.setBody(person, Person.class);

        RestResponse response = request.make(HandleFailure.ByException);
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

        RestResponse response = request.make(HandleFailure.ByException);
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
