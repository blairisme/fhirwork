package org.ucl.fhirwork.integration.ehr.model;

public class Template
{
    private String templateId;
    private String createdOn;

    public Template(String templateId, String createdOn) {
        this.templateId = templateId;
        this.createdOn = createdOn;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getCreatedOn() {
        return createdOn;
    }
}
