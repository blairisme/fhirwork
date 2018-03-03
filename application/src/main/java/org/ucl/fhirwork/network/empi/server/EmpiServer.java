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

import org.ucl.fhirwork.common.http.RestException;
import org.ucl.fhirwork.network.empi.data.Person;
import org.ucl.fhirwork.network.empi.exception.PersonExistsException;
import org.ucl.fhirwork.network.empi.exception.PersonMissingException;

import java.util.Collection;
import java.util.List;

/**
 * Instances of this class represent an EMPI server. Methods exists to create,
 * read, update and delete patient data.
 *
 * @author Blair Butterworth
 */
public interface EmpiServer
{
    /**
     * Sets the address and authentication information used to connect to the
     * EMPI server.
     *
     * @param address   the URL of an EMPI server.
     * @param username  the name of an account on the EMPI server.
     * @param password  the password of an EMPI account.
     */
    void setConnectionDetails(String address, String username, String password);

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
    Person addPerson(Person person) throws RestException, PersonExistsException;

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
    Collection<Person> findPersons(Person person) throws RestException;

    /**
     * Returns biographical information on the {@link Person} with the given
     * EMPI identifier (internal EMPI identifier)
     *
     * @param personId  the identifier of the {@code Person} to load.
     * @return          a {@code Person} instance contain information on the
     *                  desired person.
     *
     * @throws RestException            thrown if an error occurs whilst
     *                                  communicating with the EMPI server.
     * @throws PersonMissingException   thrown if a person with the given id
     *                                  cannot be found.
     */
    Person loadPerson(String personId) throws RestException, PersonMissingException;

    /**
     * Returns biographical information on all people in the EMPI system.
     *
     * @param index             the index to read people from.
     * @param count             the maximum number of people to read.
     * @return                  a list of people.
     * @throws RestException    thrown if an error occurs whilst communicating
     *                          with the EMPI server.
     */
    List<Person> loadAllPersons(int index, int count) throws RestException;

    /**
     * This method removes a {@@link Person} from the EMPI system. The system
     * locates the {@code Person} record using their internal unique id. If the
     * record is found, the record is removed from the system completely.
     *
     * @param personId          the identifier of the {@code Person} to remove.
     * @throws RestException    thrown if an error occurs whilst communicating
     *                          with the EMPI server.
     */
    void removePerson(String personId) throws RestException;

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
    Person updatePerson(Person person) throws RestException, PersonMissingException;
}
