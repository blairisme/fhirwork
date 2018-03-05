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

/**
 * Instances of this class represent biographical information stored on each
 * patient in the EMPI system. The create, read and update EMPI web services
 * accept and return Persons in the following format.
 *
 * <pre>{@code
 *
 *<person>
 *     <personId>123</personId>
 *     <personIdentifiers> ... </personIdentifiers>
 *     <dateChanged>2018-02-01T00:00:00Z</dateChanged>
 *     <dateCreated>2017-07-19T21:49:41.729Z</dateCreated>
 *
 *     <prefix>Miss</prefix>
 *     <givenName>Kathrin</givenName>
 *     <middleName>Mary</middleName>
 *     <familyName>Williams</familyName>
 *     <gender> ... </gender>
 *
 *     <address1>Kings Cross</address1>
 *     <address2>Penton Rise</address2>
 *     <birthOrder>2</birthOrder>
 *     <city>London</city>
 *     <country>UK</country>
 *     <postalCode>589632</postalCode>
 *     <state>London</state>
 *
 *     <email>kathrinwilliams@gmail.com</email>
 *     <phoneNumber>678975459787</phoneNumber>
 *
 *     <dateOfBirth>1970-09-24T00:00:00Z</dateOfBirth>
 *     <deathTime>1982-08-25T07:06:12Z</deathTime>
 *     <maritalStatusCode>divorced</maritalStatusCode>
 *     <mothersMaidenName>Katy</mothersMaidenName>
 * </person>
 *
 * }</pre>
 *
 * @author Blair Butterworth
 */
@XmlRootElement(name = "person")
@SuppressWarnings("unused")
public class Person
{
    private String personId;
    private Identifier[] personIdentifiers;
    private String givenName;
    private String familyName;
    private Gender gender;
    private String dateOfBirth;
    private String dateChanged;

    public Person() {
    }

    public Person(Person person) {
        this.personId = person.personId;
        this.personIdentifiers = person.personIdentifiers;
        this.givenName = person.givenName;
        this.familyName = person.familyName;
        this.gender = person.gender;
        this.dateOfBirth = person.dateOfBirth;
        this.dateChanged = person.dateChanged;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public InternalIdentifier getInternalIdentifier() {
        return new InternalIdentifier(personId);
    }

    public Identifier[] getPersonIdentifiers() {
        return personIdentifiers;
    }

    public void setPersonIdentifiers(Identifier[] personIdentifiers) {
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(String dateChanged) {
        this.dateChanged = dateChanged;
    }
}