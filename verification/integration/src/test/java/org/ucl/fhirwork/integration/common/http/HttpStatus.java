/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.common.http;

public class HttpStatus
{
    public static boolean isSuccessful(int code)
    {
        return (code >= 200 && code <= 299);
    }
}
