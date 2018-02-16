package org.ucl.fhirwork.integration.ehr.model;

import java.util.List;

public class Templates
{
    private List<Template> templates;

    public Templates(List<Template> templates) {
        this.templates = templates;
    }

    public List<Template> getTemplates() {
        return templates;
    }
}