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
import ca.uhn.fhir.model.primitive.StringDt;
import org.ucl.fhirwork.network.empi.data.Gender;
import org.ucl.fhirwork.network.empi.data.Identifier;
import org.ucl.fhirwork.network.empi.data.Person;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Instances of this class create {@link Person} instances, usually by
 * converting other objects.
 *
 * @author Blair Butterworth
 */
public class PersonFactory
{
    private IdentifierFactory identifierFactory;

    @Inject
    public PersonFactory(IdentifierFactory identifierFactory)
    {
        this.identifierFactory = identifierFactory;
    }

    public Person fromId(IdDt id)
    {
        Person result = new Person();
        setId(result, id);
        return result;
    }

    public Person fromPatient(Patient patient)
    {
        Person result = new Person();
        setId(result, patient);
        setIdentifiers(result, patient);
        setName(result, patient);
        return result;
    }

    private void setId(Person person, Patient patient)
    {
        setId(person, patient.getId());
    }

    private void setId(Person person, IdDt id)
    {
        person.setPersonId(id.getIdPart());
    }

    private void setIdentifiers(Person person, Patient patient)
    {
        List<Identifier> converted = new ArrayList<>();
        List<IdentifierDt> identifiers = patient.getIdentifier();
        for (IdentifierDt identifier: identifiers)
        {
            Identifier id = identifierFactory.fromId(identifier);
            converted.add(id);
        }
        person.setPersonIdentifiers(converted.toArray(new Identifier[0]));
    }

    private void setName(Person person, Patient patient)
    {
        List<HumanNameDt> names = patient.getName();
        if (! names.isEmpty())
        {
            HumanNameDt name = names.get(0);
            setFirstName(person, name);
            setLastName(person, name);
        }
    }

    private void setFirstName(Person person, HumanNameDt name)
    {
        List<StringDt> givenNames = name.getGiven();
        if (! givenNames.isEmpty())
        {
            String firstName = combineNames(givenNames);
            person.setGivenName(firstName);
        }
    }

    private void setLastName(Person person, HumanNameDt name)
    {
        List<StringDt> familyNames = name.getFamily();
        if (! familyNames.isEmpty())
        {
            String familyName = combineNames(familyNames);
            person.setFamilyName(familyName);
        }
    }

    private String combineNames(List<StringDt> names)
    {
        StringBuilder builder = new StringBuilder();
        for (StringDt name: names)
        {
            builder.append(name);
            builder.append(" ");
        }
        String result = builder.toString();
        return result.trim();
    }
}
