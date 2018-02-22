/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.common.serialization.JsonSerializer;
import org.ucl.fhirwork.common.serialization.Serializer;
import org.ucl.fhirwork.configuration.data.NetworkConfig;
import org.ucl.fhirwork.test.TestResourceUtils;

import java.io.IOException;

public class NetworkConfigTest
{
    @Test
    public void serializationTest() throws IOException
    {
        Serializer serializer = new JsonSerializer();
        String network = TestResourceUtils.readResource("configuration/network.json");

        NetworkConfig deserialized = serializer.deserialize(network, NetworkConfig.class);
        Assert.assertEquals("http://localhost:8888", deserialized.getEhr().getAddress());
        Assert.assertEquals("http://localhost:8080", deserialized.getEmpi().getAddress());
    }
}
