/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.common.framework;

/**
 * Instances of this exception are thrown when an unexpected error occurs while
 * executing an user action. The underlying reason for the error can be
 * obtained using the {@link #getCause()} method.
 *
 * @author Blair Butterworth
 */
public class ExecutionException extends Exception
{
    public ExecutionException(Throwable cause)
    {
        super(cause);
    }
}
