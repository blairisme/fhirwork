package org.ucl.fhirwork.network.empi.exception;

public class PersonExistsException extends RuntimeException
{
    public PersonExistsException(String personId)
    {
        super("Person already exists: " + personId);
    }
}
