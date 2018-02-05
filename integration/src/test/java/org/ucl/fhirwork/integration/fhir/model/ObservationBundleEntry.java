package org.ucl.fhirwork.integration.fhir.model;

public class ObservationBundleEntry
{
    private String fullUrl;
    private Observation resource;

    public ObservationBundleEntry(String fullUrl, Observation resource) {
        this.fullUrl = fullUrl;
        this.resource = resource;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public Observation getResource() {
        return resource;
    }
}
