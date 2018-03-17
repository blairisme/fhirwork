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

public class AuthenticationRequestTest extends EmpiDataTest<AuthenticationRequest>
{
    @Override
    protected Class<AuthenticationRequest> getObjectType() {
        return AuthenticationRequest.class;
    }

    @Override
    protected Serializer getSerializer() {
        return new XmlSerializer();
    }

    @Override
    protected String getSerialized() throws IOException {
        return TestResourceUtils.readResource("empi/AuthenticationRequestExample.xml");
    }

    @Override
    protected AuthenticationRequest getDeserialized() {
        return new AuthenticationRequest("foo", "bar");
    }
}
