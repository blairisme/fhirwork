/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.common.network.exception;

public class UnexpectedResponseException extends NetworkException
{
    public UnexpectedResponseException(String message)
    {
        super(message);
    }

    public UnexpectedResponseException(String operation, String search)
    {
        this("Illegal result of type person" + operation + ": " + search);
    }
}
