/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.common.http;

/**
 * Instances of this exception are thrown when an unexpected error occurs while
 * communicating with a REST server. The underlying reason for the error can be
 * obtained using the {@link #getCause()} or {@link #getMessage()} methods.
 *
 * @author Blair Butterworth
 */
public class RestException extends Exception
{
    public RestException(Exception cause)
    {
        super(cause);
    }

    public RestException(int httpCode)
    {
        super("REST server error: status code " + httpCode);
    }
}
