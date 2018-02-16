package org.ucl.fhirwork.integration.ehr.model;

public class Composition
{
    private Identifier uid;

    public Composition(Identifier uid) {
        this.uid = uid;
    }

    public Identifier getUid() {
        return uid;
    }
}
