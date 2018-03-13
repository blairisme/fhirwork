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

/**
 * Instances of this error are throw when an issue occurs communicating with a
 * remote service.
 *
 * @author Blair Butterworth
 */
public class NetworkException extends RuntimeException
{
    public NetworkException(String message) {
        super(message);
    }
}
