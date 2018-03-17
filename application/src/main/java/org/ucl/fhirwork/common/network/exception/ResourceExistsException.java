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
 * Instances of this error are thrown when a performing a network action that
 * could not complete because a preexisting resource already exists.
 *
 * @author Blair Butterworth
 */
public class ResourceExistsException extends NetworkException
{
    public ResourceExistsException(String type, String id) {
        super(type + " already exists: " + id);
    }
}
