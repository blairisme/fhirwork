package org.ucl.fhirwork.integration.ehr.model;

public class HealthRecord
{
    private String ehrId;

    public HealthRecord(String ehrId) {
        this.ehrId = ehrId;
    }

    public String getEhrId() {
        return ehrId;
    }
}
