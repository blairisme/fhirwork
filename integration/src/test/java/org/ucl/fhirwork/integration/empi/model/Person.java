/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.empi.model;

import cucumber.api.java.en.Given;
import org.ucl.fhirwork.integration.cucumber.Profile;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "person")
@SuppressWarnings("unused")
public class Person
{
    private String personId;
    private Identifier personIdentifiers;
    private String givenName;
    private String familyName;
    private Gender gender;

    public Person()
    {
        this.personId = null;
        this.personIdentifiers = null;
        this.givenName = null;
        this.familyName = null;
        this.gender = null;
    }

    public Person(String personId, Identifier personIdentifiers, String givenName, String familyName, Gender gender)
    {
        this.personId = personId;
        this.givenName = givenName;
        this.familyName = familyName;
        this.personIdentifiers = personIdentifiers;
        this.gender = gender;
    }

    public static Person fromProfile(Profile profile)
    {
        return new Person(
            profile.getId(),
            Identifier.fromToken(profile.getId()),
            profile.getFirst(),
            profile.getLast(),
            Gender.fromName(profile.getGender()));
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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