package org.ucl.fhirwork.integration.fhir.model;

public class Text
{
    private String status;
    private String div;

    public Text(String status, String div) {
        this.status = status;
        this.div = div;
    }

    public String getStatus() {
        return status;
    }

    public String getDiv() {
        return div;
    }
}
