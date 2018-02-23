/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.common.reflect;

/**
 * Instances of this class provide helper methods for type inspection.
 *
 * @author Blair Butterworth
 */
public class TypeUtils
{
    public static <T> T newInstance(Class<T> clazz) throws ReflectionException
    {
        try {
            return clazz.newInstance();
        }
        catch (InstantiationException e) {
            throw new ReflectionException(e);
        }
        catch (IllegalAccessException e) {
            throw new ReflectionException(e);
        }
    }
}
