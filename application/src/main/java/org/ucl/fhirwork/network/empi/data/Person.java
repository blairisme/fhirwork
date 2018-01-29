/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.empi.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "person")
@SuppressWarnings("unused")
public class Person
{
    private String personId;
    //private Identifier personIdentifiers;
    private String givenName;
    private String familyName;
    //private Gender gender;

    public Person()
    {
        this.personId = null;
        //this.personIdentifiers = null;
        this.givenName = null;
        this.familyName = null;
        //this.gender = null;
    }

    //public Person(String personId, Identifier personIdentifiers, String givenName, String familyName, Gender gender)
    public Person(String personId, String givenName, String familyName)
    {
        this.personId = personId;
        this.givenName = givenName;
        this.familyName = familyName;
        //this.personIdentifiers = personIdentifiers;
        //this.gender = gender;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

//    public Identifier getPersonIdentifiers() {
//        return personIdentifiers;
//    }
//
//    public void setPersonIdentifiers(Identifier personIdentifiers) {
//        this.personIdentifiers = personIdentifiers;
//    }
//
//    public Gender getGender() {
//        return gender;
//    }
//
//    public void setGender(Gender gender) {
//        this.gender = gender;
//    }

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