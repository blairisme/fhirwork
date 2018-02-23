/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.ehr.data;

import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.common.serialization.JsonSerializer;
import org.ucl.fhirwork.test.TestResourceUtils;

import java.io.IOException;

public class QueryBundleTest
{
    @Test
    public void serializationTest() throws IOException
    {
        JsonSerializer serializer = new JsonSerializer();
        String bundle = TestResourceUtils.readResource("ehr/QueryBundle.json");

        ObservationBundle deserialized = serializer.deserialize(bundle, ObservationBundle.class);
        Assert.assertEquals(6, deserialized.getResultSet().size());
    }
}
