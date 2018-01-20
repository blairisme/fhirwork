/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.empi.model;

import org.ucl.fhirwork.integration.data.Patient;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "person")
@SuppressWarnings("unused")
public class Person
{
    private String personId;
    private Identifier personIdentifiers;
    private String givenName;
    private String familyName;

    public Person()
    {
        this.personId = null;
        this.personIdentifiers = null;
        this.givenName = null;
        this.familyName = null;
    }

    public Person(Patient patient)
    {
        this.personId = patient.getId();
        this.givenName = patient.getFirst();
        this.familyName = patient.getLast();
        this.personIdentifiers = new Identifier(patient.getId(), new IdentifierDomain("2.16.840.1.113883.4.1"));
    }

    public Person(String personId, Identifier personIdentifiers, String givenName, String familyName)
    {
        this.personId = personId;
        this.givenName = givenName;
        this.familyName = familyName;
        this.personIdentifiers = personIdentifiers;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
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