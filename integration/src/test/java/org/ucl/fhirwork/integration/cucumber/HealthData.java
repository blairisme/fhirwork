package org.ucl.fhirwork.integration.cucumber;

public class HealthRecord
{
    private String id;
    private String subject;
    private String namespace;

    public HealthRecord(String id, String subject, String namespace) {
        this.id = id;
        this.subject = subject;
        this.namespace = namespace;
    }

    public String getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getNamespace() {
        return namespace;
    }
}
