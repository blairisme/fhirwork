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
