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

public class IdentifierTest extends EmpiDataTest<Identifier>
{
    @Override
    protected Class<Identifier> getObjectType() {
        return Identifier.class;
    }

    @Override
    protected Serializer getSerializer() {
        return new XmlSerializer();
    }

    @Override
    protected String getSerialized() throws IOException {
        return TestResourceUtils.readResource("empi/IdentifierExample.xml");
    }

    @Override
    protected Identifier getDeserialized() {
        IdentifierDomain domain = new IdentifierDomain("13", "OpenMRS", "35a02490", "35a02490", "OpenMRS");
        return new Identifier("568749875445698798988873", "2017-07-19T21:49:41.729Z", domain);
    }
}
