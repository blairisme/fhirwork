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
 *     <dateChanged>2017-07-19T21:49:41.746Z</dateChanged>
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
    private String dateChanged;

    private String address1;
    private String address2;
    private String city;
    private String state;
    private String country;
    private String postalCode;

    public Person() {
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
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

    public String getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(String dateChanged) {
        this.dateChanged = dateChanged;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}