package org.ucl.fhirwork.integration.ehr;

import org.ucl.fhirwork.integration.common.lang.StringCovertable;

public enum EhrHeader implements StringCovertable
{
    SessionId ("Ehr-Session");

    private String value;

    private EhrHeader(String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return value;
    }
}
