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

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.common.serialization.XmlSerializer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PersonTest
{
    @Test
    public void serializationTest() throws IOException
    {
        XmlSerializer serializer = new XmlSerializer();
        String person = readResource("empi/PersonExample.xml");

        Person deserialized = serializer.deserialize(person, Person.class);
        Assert.assertEquals("Kathrin", deserialized.getGivenName());
        Assert.assertEquals("Williams", deserialized.getFamilyName());
        Assert.assertEquals(2, deserialized.getPersonIdentifiers().length);

        String serialized = serializer.serialize(deserialized, Person.class);
        Assert.assertTrue(serialized.contains("<givenName>Kathrin</givenName>"));
        Assert.assertTrue(serialized.contains("<familyName>Williams</familyName>"));
    }

    private String readResource(String resource) throws IOException
    {
        URL templateUrl = Thread.currentThread().getContextClassLoader().getResource(resource);
        File templateFile = new File(templateUrl.getPath());
        return FileUtils.readFileToString(templateFile, StandardCharsets.UTF_8);
    }
}
