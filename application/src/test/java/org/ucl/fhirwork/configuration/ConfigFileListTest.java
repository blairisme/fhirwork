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
import org.ucl.fhirwork.configuration.persistence.ConfigFileList;
import org.ucl.fhirwork.test.TestResourceUtils;

import java.io.IOException;

public class ConfigFileListTest
{
    @Test
    public void serializationTest() throws IOException
    {
        Serializer serializer = new JsonSerializer();
        String manifest = TestResourceUtils.readResource("configuration/manifest.json");

        ConfigFileList deserialized = serializer.deserialize(manifest, ConfigFileList.class);
        Assert.assertEquals(2, deserialized.getTestingConfig().size());
        Assert.assertEquals(2, deserialized.getDevelopmentConfig().size());
        Assert.assertEquals(2, deserialized.getProductionConfig().size());
    }
}
