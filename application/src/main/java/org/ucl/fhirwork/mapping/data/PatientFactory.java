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
import ca.uhn.fhir.model.primitive.IdDt;
import org.ucl.fhirwork.network.empi.data.Person;

/**
 * Instances of this class create {@link Patient} instances, usually by
 * converting other objects.
 *
 * @author Blair Butterworth
 */
public class PatientFactory
{
    public Patient newPatient(Person person)
    {
        IdDt id = new IdDt(person.getPersonId());

        Patient result = new Patient();
        result.setId(id);

        return result;
    }
}
