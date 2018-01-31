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

import ca.uhn.fhir.model.dstu2.composite.HumanNameDt;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.primitive.IdDt;
import org.ucl.fhirwork.network.empi.data.Identifier;
import org.ucl.fhirwork.network.empi.data.Person;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Instances of this class create {@link Patient} instances, usually by
 * converting other objects.
 *
 * @author Blair Butterworth
 */
public class PatientFactory
{
    private IdentifierFactory identifierFactory;

    @Inject
    public PatientFactory(IdentifierFactory identifierFactory)
    {
        this.identifierFactory = identifierFactory;
    }

    public Patient fromPerson(Person person)
    {
        Patient result = new Patient();
        setId(result, person);
        setIdentifiers(result, person);
        setName(result, person);
        return result;
    }

    private void setId(Patient patient, Person person)
    {
        patient.setId(new IdDt(person.getPersonId()));
    }

    private void setIdentifiers(Patient patient, Person person)
    {
        List<IdentifierDt> identifiers = new ArrayList<>();
        for (Identifier personIdentifier: person.getPersonIdentifiers())
        {
            IdentifierDt identifier = identifierFactory.fromIdentifier(personIdentifier);
            identifiers.add(identifier);
        }
        patient.setIdentifier(identifiers);
    }

    private void setName(Patient patient, Person person)
    {
        HumanNameDt name = new HumanNameDt();
        name.addGiven(person.getGivenName());
        name.addFamily(person.getFamilyName());
        patient.setName(Arrays.asList(name));
    }
}
