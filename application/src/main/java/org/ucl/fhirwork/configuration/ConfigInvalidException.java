/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.configuration;

/**
 * Instances of this exception are thrown if persisted configuration failed to
 * deserialize.
 *
 * @author Chenghui Fan
 * @author Blair Butterworth
 */
public class ConfigInvalidException extends RuntimeException
{
    public ConfigInvalidException(Throwable cause)
    {
        super(cause);
    }
}
