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
