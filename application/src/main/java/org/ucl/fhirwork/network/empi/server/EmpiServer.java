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
    //TODO: Documentation suggests that if the person is known nothing will be returned. Handle this case
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
