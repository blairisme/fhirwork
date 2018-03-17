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

public class PersonTest extends EmpiDataTest<Person>
{
    @Override
    protected Class<Person> getObjectType() {
        return Person.class;
    }

    @Override
    protected Serializer getSerializer() {
        return new XmlSerializer();
    }

    @Override
    protected String getSerialized() throws IOException {
        return TestResourceUtils.readResource("empi/PersonExample.xml");
    }

    @Override
    protected Person getDeserialized() {
        return PersonDataFactory.newPerson("123", "Kathrin", "Williams");
    }
}
