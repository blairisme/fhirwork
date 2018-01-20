/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.fhir;

public class FhirServerException extends Exception
{
    public FhirServerException(Throwable cause)
    {
        super(cause);
    }

    public FhirServerException(int httpCode)
    {
        super("FHIR server error: status code " + httpCode);
    }
}
