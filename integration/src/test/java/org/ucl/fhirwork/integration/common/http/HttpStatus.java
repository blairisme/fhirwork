package org.ucl.fhirwork.integration.common.http;

public class HttpStatus
{
    public static boolean isSuccessful(int code)
    {
        return (code >= 200 && code <= 299);
    }
}
