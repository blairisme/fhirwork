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

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.common.serialization.Serializer;

public abstract class EmpiDataTest <T>
{
    protected abstract Class<T> getObjectType();

    protected abstract Serializer getSerializer();

    protected abstract String getSerialized() throws Exception;

    protected abstract T getDeserialized();

    @Test
    public void serializationTest() throws Exception
    {
        Serializer serializer = getSerializer();
        String serialized = getSerialized();

        T expected = getDeserialized();
        T actual = serializer.deserialize(serialized, getObjectType());

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deserializationTest() throws Exception
    {
        Serializer serializer = getSerializer();
        T deserialized = getDeserialized();

        String expected = getSerialized();
        String actual = serializer.serialize(deserialized, getObjectType());

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void equalsAndHashCodeTest() {
        EqualsVerifier verifier = EqualsVerifier.forClass(Person.class);
        verifier.verify();
    }
}
