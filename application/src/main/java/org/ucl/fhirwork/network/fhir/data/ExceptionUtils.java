package org.ucl.fhirwork.network.fhir.data;

import ca.uhn.fhir.rest.server.exceptions.*;
import org.ucl.fhirwork.common.framework.ExecutionException;
import org.ucl.fhirwork.common.network.exception.AmbiguousResultException;
import org.ucl.fhirwork.common.network.exception.AuthenticationException;
import org.ucl.fhirwork.common.network.exception.ResourceExistsException;
import org.ucl.fhirwork.common.network.exception.ResourceMissingException;

public class ExceptionUtils
{
    public static BaseServerResponseException getFrameworkException(Throwable error)
    {
        if (error instanceof BaseServerResponseException) {
            return (BaseServerResponseException)error;
        }
        if (error instanceof ExecutionException) {
            return getFrameworkException(error.getCause());
        }
        if (error instanceof ResourceExistsException) {
            return new InvalidRequestException(error.getMessage());
        }
        if (error instanceof ResourceMissingException) {
            return new ResourceNotFoundException(error.getMessage());
        }
        if (error instanceof AmbiguousResultException) {
            return new PreconditionFailedException(error.getMessage());
        }
        if (error instanceof AuthenticationException) {
            return new ca.uhn.fhir.rest.server.exceptions.AuthenticationException(error.getMessage());
        }
        if (error != null) {
            return new InternalErrorException(error);
        }
        return new InternalErrorException("Unknown Error");
    }
}
