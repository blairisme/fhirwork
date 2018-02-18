/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.fhir;

import org.ucl.fhirwork.integration.common.lang.StringCovertable;

public enum FhirParameter implements StringCovertable
{
    Code        ("code"),
    Gender      ("gender"),
    Given       ("given"),
    Family      ("family"),
    Format      ("_format"),
    Identifier  ("identifier");

    private String value;

    FhirParameter(String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return value;
    }
}