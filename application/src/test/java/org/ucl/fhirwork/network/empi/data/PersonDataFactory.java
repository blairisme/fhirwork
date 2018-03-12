package org.ucl.fhirwork.network.empi.data;

public class PersonDataFactory
{
    public static Person newPerson(String id, String firstName, String lastName) {
        IdentifierDomain domain1 = new IdentifierDomain("13", "OpenMRS", "35a02490", "35a02490", "OpenMRS");
        Identifier identifier1 = new Identifier("568749875445698798988873", "2017-07-19T21:49:41.729Z", domain1);

        IdentifierDomain domain2 = new IdentifierDomain("18", "OpenEMPI", "2.16.840.1.113883.4.357", "2.16.840.1.113883.4.357", "hl7");
        Identifier identifier2 = new Identifier("2b869d20-6ccc-11e7-a2fc-0242ac120003", "2017-07-19T21:49:41.729Z", domain2);

        Identifier[] identifiers = {identifier1, identifier2};
        Gender gender = new Gender("1", "F", "Female", "Female");

        Person person = new Person();
        person.setPersonId(id);
        person.setPersonIdentifiers(identifiers);
        person.setGivenName(firstName);
        person.setFamilyName(lastName);
        person.setGender(gender);
        person.setDateChanged("2017-07-19T21:49:41.746Z");
        person.setDateOfBirth("2017-07-19T21:49:41.729Z");
        return person;
    }
}
