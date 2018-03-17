/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.common.resources;

/**
 * Instances of this exception are thrown if a requested resource could not be
 * found or the invoker doesn't have adequate privileges to access the
 * resource.
 *
 * @author Blair Butterworth
 */
public class ResourceNotFoundException extends RuntimeException
{
    public ResourceNotFoundException(String resource) {
        super("Resource missing:" + resource);
    }
}
