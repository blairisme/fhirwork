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

/**
 * Implementors of this interface serialize objects into their equivalent
 * textual representation. Methods are provided to convert objects into text
 * and text into objects.
 *
 * @author Blair Butterworth
 */
public interface Serializer
{
    <T> String serialize(T value, Class<T> type);

    <T> T deserialize(String value, Class<T> type);
}
