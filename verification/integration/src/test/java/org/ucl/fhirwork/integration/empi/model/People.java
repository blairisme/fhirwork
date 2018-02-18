/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.empi.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "people")
@SuppressWarnings("unused")
public class People {
    private Person[] person;

    public People() {
        this.person = new Person[0];
    }

    public People(Person[] persons) {
        this.person = persons;
    }

    public Person[] getPerson() {
        return person;
    }

    public void setPerson(Person[] persons) {
        this.person = persons;
    }
}
