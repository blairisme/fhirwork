package org.ucl.fhirwork.integration.ehr.model;

public class Identifier
{
    private String value;

    public Identifier(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
