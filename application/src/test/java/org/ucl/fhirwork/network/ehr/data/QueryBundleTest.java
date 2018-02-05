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

        //QueryBundle deserialized = serializer.deserialize(bundle, QueryBundle.class);
        //deserialized.getResultSet();

        /*
        Assert.assertEquals("Kathrin", deserialized.getGivenName());
        Assert.assertEquals("Williams", deserialized.getFamilyName());
        Assert.assertEquals(2, deserialized.getPersonIdentifiers().length);

        String serialized = serializer.serialize(deserialized, Person.class);
        Assert.assertTrue(serialized.contains("<givenName>Kathrin</givenName>"));
        Assert.assertTrue(serialized.contains("<familyName>Williams</familyName>"));
        */
    }
}
