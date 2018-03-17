package org.ucl.fhirwork.common.serialization;

import org.junit.Assert;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public abstract class SerializerTest
{
    protected abstract Serializer getSerializer();

    protected abstract SerializableObject getDeserialized();

    protected abstract String getSerialized();

    @Test
    public void serializeTest() {
        SerializableObject deserialized = getDeserialized();

        Serializer serializer = getSerializer();
        String serialized = serializer.serialize(deserialized, SerializableObject.class);

        Assert.assertNotNull(serialized);
        Assert.assertEquals(getSerialized(), serialized);
    }

    @Test
    public void serializeWriterTest() {
        Writer writer = new StringWriter();
        SerializableObject deserialized = getDeserialized();

        Serializer serializer = getSerializer();
        serializer.serialize(deserialized, SerializableObject.class, writer);

        String serialized = writer.toString();
        Assert.assertNotNull(serialized);
        Assert.assertEquals(getSerialized(), serialized);
    }

    @Test
    public void deserializeTest() {
        String serialized = getSerialized();
        SerializableObject expected = getDeserialized();

        Serializer serializer = getSerializer();
        SerializableObject actual = serializer.deserialize(serialized, SerializableObject.class);

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getAddress(), actual.getAddress());
    }

    @Test
    public void deserializeReaderTest() {
        String serialized = getSerialized();
        SerializableObject expected = getDeserialized();
        Reader reader = new StringReader(serialized);

        Serializer serializer = getSerializer();
        SerializableObject actual = serializer.deserialize(reader, SerializableObject.class);

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getAddress(), actual.getAddress());
    }

    @Test (expected = SerializationException.class)
    public void deserializeCorruptTest() {
        String corrupt = "corrupt";

        Serializer serializer = getSerializer();
        serializer.deserialize(corrupt, SerializableObject.class);
    }

    @Test (expected = SerializationException.class)
    public void deserializeReaderCorruptTest() {
        String corrupt = "corrupt";
        Reader reader = new StringReader(corrupt);

        Serializer serializer = getSerializer();
        serializer.deserialize(reader, SerializableObject.class);
    }
}
