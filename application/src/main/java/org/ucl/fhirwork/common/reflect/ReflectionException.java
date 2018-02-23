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
 * Instances of this class are thrown if an error occurs while preforming
 * reflection.
 *
 * @author Blair Butterworth
 */
public class ReflectionException extends RuntimeException
{
    public ReflectionException(Throwable cause) {
        super(cause);
    }
}
