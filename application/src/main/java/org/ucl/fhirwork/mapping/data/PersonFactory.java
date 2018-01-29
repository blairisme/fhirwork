/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping.data;

import ca.uhn.fhir.model.dstu2.resource.Patient;
import org.ucl.fhirwork.network.empi.data.Gender;
import org.ucl.fhirwork.network.empi.data.Identifier;
import org.ucl.fhirwork.network.empi.data.Person;

/**
 * Instances of this class create {@link Person} instances, usually by
 * converting other objects.
 *
 * @author Blair Butterworth
 */
public class PersonFactory
{
    public Person newPerson(Patient patient)
    {
        String id = patient.getId().getIdPart();
        return new Person(
                id,
                Identifier.fromToken(id),
                "foo",
                "bar",
                Gender.fromName(patient.getGender()));
    }
}
