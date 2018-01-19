package org.ucl.fhirwork.integration.empi.model;

import org.ucl.fhirwork.integration.data.Patient;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "person")
public class Person
{
    private Identifier personIdentifiers;
    private String givenName;
    private String familyName;

    public Person() {
        this.personIdentifiers = null;
        this.givenName = null;
        this.familyName = null;
    }

    public Person(Patient patient)
    {
        this.givenName = patient.getFirst();
        this.familyName = patient.getLast();
        this.personIdentifiers = new Identifier(patient.getId(), new IdentifierDomain("2.16.840.1.113883.4.1"));
    }

    public Person(Identifier personIdentifiers, String givenName, String familyName) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.personIdentifiers = personIdentifiers;
    }

    public Identifier getPersonIdentifiers() {
        return personIdentifiers;
    }

    public void setPersonIdentifiers(Identifier personIdentifiers) {
        this.personIdentifiers = personIdentifiers;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
}