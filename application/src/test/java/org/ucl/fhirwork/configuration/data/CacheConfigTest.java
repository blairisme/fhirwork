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

import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.common.serialization.JsonSerializer;
import org.ucl.fhirwork.common.serialization.Serializer;
import org.ucl.fhirwork.test.TestResourceUtils;

import java.io.IOException;

public class CacheConfigTest
{
    @Test
    public void serializationTest() throws IOException
    {
        Serializer serializer = new JsonSerializer();
        String general = TestResourceUtils.readResource("configuration/cache.json");

        CacheConfig deserialized = serializer.deserialize(general, CacheConfig.class);
        Assert.assertEquals(true, deserialized.isEmpiCacheEnabled());
        Assert.assertEquals(10000, deserialized.getEmpiCacheSize());
        Assert.assertEquals(1440, deserialized.getEmpiCacheExpiry());
    }
}
