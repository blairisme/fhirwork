package org.ucl.fhirwork.common.network.exception;

public class UnexpectedResponseException extends NetworkException
{
    public UnexpectedResponseException(String message)
    {
        super(message);
    }

    public UnexpectedResponseException(String operation, String search)
    {
        this("Unexpected result for " + operation + ": " + search);
    }
}
