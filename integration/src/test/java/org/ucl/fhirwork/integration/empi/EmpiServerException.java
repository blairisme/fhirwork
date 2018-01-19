/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.empi;

public class EmpiServerException extends Exception
{
    public EmpiServerException(Exception cause)
    {
        super(cause);
    }

    public EmpiServerException(int httpCode)
    {
        super("EMPI server error: status code " + httpCode);
    }
}
