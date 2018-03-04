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
import ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum;
import ca.uhn.fhir.model.primitive.BoundCodeDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.rest.param.TokenParam;
import org.ucl.fhirwork.network.empi.data.Gender;
import org.ucl.fhirwork.network.empi.data.Identifier;
import org.ucl.fhirwork.network.empi.data.Person;
import org.ucl.fhirwork.network.fhir.data.SearchParameter;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Instances of this class create {@link Person} instances, usually by
 * converting other objects.
 *
 * @author Blair Butterworth
 */
public class PersonFactory
{
    private GenderFactory genderFactory;
    private IdentifierFactory identifierFactory;

    @Inject
    public PersonFactory(GenderFactory genderFactory, IdentifierFactory identifierFactory)
    {
        this.genderFactory = genderFactory;
        this.identifierFactory = identifierFactory;
    }

    public Person fromPatient(Patient patient)
    {
        Person result = new Person();
        setId(result, patient);
        setIdentifiers(result, patient);
        setName(result, patient);
        setGender(result, patient);
        return result;
    }

    public Person fromSearchParameters(Map<SearchParameter, Object> searchParameters)
    {
        Person result = new Person();
        setId(result, searchParameters);
        setFirstName(result, searchParameters);
        setLastName(result, searchParameters);
        setGender(result, searchParameters);
        return result;
    }

    public Person update(Person person, Patient patient)
    {
        Person result = new Person(person);
        setId(result, patient);
        setIdentifiers(result, patient);
        setName(result, patient);
        setGender(result, patient);
        setDateChanged(person);
        return result;
    }

    private void setId(Person person, Patient patient)
    {
        IdDt identifier = patient.getId();
        if (identifier != null){
            person.setPersonId(identifier.getIdPart());
        }
    }

    private void setId(Person person, Map<SearchParameter, Object> searchParameters)
    {
        TokenParam identifierParameter = (TokenParam)searchParameters.get(SearchParameter.Identifier);
        if (identifierParameter != null)
        {
            Identifier identifier = identifierFactory.fromSearchParameter(identifierParameter);
            Identifier[] identifiers = {identifier};
            person.setPersonIdentifiers(identifiers);
        }
    }

    private void setIdentifiers(Person person, Patient patient)
    {
        List<Identifier> converted = new ArrayList<>();
        List<IdentifierDt> identifiers = patient.getIdentifier();
        for (IdentifierDt identifier: identifiers)
        {
            Identifier id = identifierFactory.fromIdentifier(identifier);
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

    private void setFirstName(Person person, Map<SearchParameter, Object> searchParameters)
    {
        StringDt givenName = (StringDt)searchParameters.get(SearchParameter.GivenName);
        if (givenName != null) {
            person.setGivenName(givenName.getValue());
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

    private void setLastName(Person person, Map<SearchParameter, Object> searchParameters)
    {
        StringDt familyName = (StringDt)searchParameters.get(SearchParameter.FamilyName);
        if (familyName != null) {
            person.setFamilyName(familyName.getValue());
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

    private void setGender(Person person, Patient patient)
    {
        BoundCodeDt<AdministrativeGenderEnum> genderContainer = patient.getGenderElement();
        AdministrativeGenderEnum patientGender = genderContainer.getValueAsEnum();

        if (patientGender != null)
        {
            Gender gender = genderFactory.fromEnum(patientGender);
            person.setGender(gender);
        }
    }

    private void setGender(Person person, Map<SearchParameter, Object> searchParameters)
    {
        StringDt genderCode = (StringDt)searchParameters.get(SearchParameter.Gender);
        if (genderCode != null) {
            Gender gender = genderFactory.fromCode(genderCode);
            person.setGender(gender);
        }
    }

    private void setDateChanged(Person person)
    {
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS]'Z'", Locale.ENGLISH);
        //String dateNow = formatter.format(LocalDateTime.now());
        String dateNow = "2019-03-04T08:38:02.705Z";
        person.setDateChanged(dateNow);
    }



}
