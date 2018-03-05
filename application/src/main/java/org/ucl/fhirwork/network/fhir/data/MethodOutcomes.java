/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.fhir.data;

import ca.uhn.fhir.model.dstu2.resource.BaseResource;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.exceptions.*;
import org.ucl.fhirwork.common.framework.ExecutionException;
import org.ucl.fhirwork.common.network.exception.AmbiguousResultException;
import org.ucl.fhirwork.common.network.exception.AuthenticationException;
import org.ucl.fhirwork.common.network.exception.ResourceExistsException;
import org.ucl.fhirwork.common.network.exception.ResourceMissingException;

public class MethodOutcomes
{
    public static MethodOutcome identifier(BaseResource resource)
    {
        MethodOutcome result = new MethodOutcome();
        result.setId(resource.getId());
        return result;
    }

    public static BaseServerResponseException error(Throwable throwable)
    {
        if (throwable instanceof BaseServerResponseException) {
            return (BaseServerResponseException)throwable;
        }
        if (throwable instanceof ExecutionException) {
            return error(throwable.getCause());
        }
        if (throwable instanceof ResourceExistsException) {
            return new InvalidRequestException(throwable.getMessage());
        }
        if (throwable instanceof ResourceMissingException) {
            return new ResourceNotFoundException(throwable.getMessage());
        }
        if (throwable instanceof AmbiguousResultException) {
            return new PreconditionFailedException(throwable.getMessage());
        }
        if (throwable instanceof AuthenticationException) {
            return new ca.uhn.fhir.rest.server.exceptions.AuthenticationException(throwable.getMessage());
        }
        if (throwable != null) {
            return new InternalErrorException(throwable);
        }
        return new InternalErrorException("Unknown Error");
    }
}
