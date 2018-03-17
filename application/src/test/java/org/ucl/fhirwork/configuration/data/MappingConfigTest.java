/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.configuration.data;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.common.serialization.JsonSerializer;
import org.ucl.fhirwork.common.serialization.Serializer;
import org.ucl.fhirwork.configuration.data.MappingConfig;
import org.ucl.fhirwork.test.TestResourceUtils;

public class MappingConfigTest
{
    @Test
    public void serializationTest() throws IOException
    {
        Serializer serializer = new JsonSerializer();
        String mapping = TestResourceUtils.readResource("configuration/mapping.json");

        MappingConfig deserialized = serializer.deserialize(mapping, MappingConfig.class);
        Assert.assertEquals(4, deserialized.getBasic().size());
        Assert.assertEquals(1, deserialized.getScripted().size());
    }
}
