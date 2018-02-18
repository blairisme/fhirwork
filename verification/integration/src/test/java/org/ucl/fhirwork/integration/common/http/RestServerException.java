/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.common.http;

public class RestServerException extends Exception
{
    public RestServerException(Exception cause)
    {
        super(cause);
    }

    public RestServerException(int httpCode)
    {
        super("REST server error: status code " + httpCode);
    }
}
