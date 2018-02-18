/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.common.http;

import org.ucl.fhirwork.integration.common.lang.StringCovertable;

public enum HttpHeader implements StringCovertable
{
    Accept      ("accept"),
    ContentType ("Content-Type");

    private String value;

    private HttpHeader(String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return value;
    }
}

