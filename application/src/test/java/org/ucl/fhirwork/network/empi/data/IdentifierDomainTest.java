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

public class IdentifierDomainTest  extends EmpiDataTest<IdentifierDomain>
{
    @Override
    protected Class<IdentifierDomain> getObjectType() {
        return IdentifierDomain.class;
    }

    @Override
    protected Serializer getSerializer() {
        return new XmlSerializer();
    }

    @Override
    protected String getSerialized() throws IOException {
        return TestResourceUtils.readResource("empi/IdentifierDomainExample.xml");
    }

    @Override
    protected IdentifierDomain getDeserialized() {
        return new IdentifierDomain("13", "OpenMRS", "35a02490", "35a02490", "OpenMRS");
    }
}