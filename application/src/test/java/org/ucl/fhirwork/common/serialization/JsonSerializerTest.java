/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.common.serialization;

public class JsonSerializerTest extends SerializerTest
{
    @Override
    protected Serializer getSerializer() {
        return new JsonSerializer();
    }

    @Override
    protected SerializableObject getDeserialized() {
        return new SerializableObject("foo", "bar");
    }

    @Override
    protected String getSerialized() {
        return "{\"name\":\"foo\",\"address\":\"bar\"}";
    }
}
