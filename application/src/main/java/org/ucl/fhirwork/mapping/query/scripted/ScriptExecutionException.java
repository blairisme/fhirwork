/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping.query.scripted;

/**
 * Instances of this error are thrown when an exception occurs while executing
 * a script. The exact cause of the error can be obtained using
 * {@link ScriptExecutionException#getCause()}.
 *
 * @author Blair Butterworth
 */
public class ScriptExecutionException extends RuntimeException
{
    public ScriptExecutionException(Throwable cause) {
        super(cause);
    }
}
