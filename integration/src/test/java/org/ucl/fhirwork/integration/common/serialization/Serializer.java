/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.common.serialization;

public interface Serializer
{
    <T> String serialize(T value, Class<T> type);

    <T> T deserialize(String value, Class<T> type);
}
