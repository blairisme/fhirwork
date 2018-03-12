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

import org.ucl.fhirwork.common.serialization.Serializer;
import org.ucl.fhirwork.common.serialization.XmlSerializer;
import org.ucl.fhirwork.test.TestResourceUtils;

import java.io.IOException;

public class PeopleTest  extends EmpiDataTest<People> {
    @Override
    protected Class<People> getObjectType() {
        return People.class;
    }

    @Override
    protected Serializer getSerializer() {
        return new XmlSerializer();
    }

    @Override
    protected String getSerialized() throws IOException {
        return TestResourceUtils.readResource("empi/PeopleExample.xml");
    }

    @Override
    protected People getDeserialized() {
        Person person1 = PersonDataFactory.newPerson("123", "Kathrin", "Williams");
        Person person2 = PersonDataFactory.newPerson("999", "Lilly", "Smith");
        Person[] persons = {person1, person2};
        return new People(persons);
    }
}