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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.ucl.fhirwork.network.empi.server.EmpiServer;

import javax.annotation.concurrent.Immutable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Instances of this class represent a collection of {@link Person people},
 * commonly returned when searching for people using an {@link EmpiServer}.
 *
 * @author Blair Butterworth
 */
@Immutable
@XmlRootElement(name = "people")
@SuppressWarnings("unused")
public final class People {
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;

        if (object instanceof People) {
            People other = (People)object;
            return new EqualsBuilder()
                .append(this.person, other.person)
                .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(person)
            .toHashCode();
    }
}
