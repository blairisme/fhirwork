/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.fhir;

import org.ucl.fhirwork.integration.common.http.RestEndpoint;

public enum FhirEndpoint implements RestEndpoint
{
    Patient("fhir/Patient");

    private String path;

    private FhirEndpoint(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return path;
    }
}
