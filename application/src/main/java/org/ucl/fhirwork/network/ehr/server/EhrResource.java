package org.ucl.fhirwork.network.ehr.server;

import org.ucl.fhirwork.common.http.RestResource;

public enum EhrResource implements RestResource {

    Composition ("composition"),
    Ehr         ("ehr"),
    Observation ("observation"),
    Query       ("query"),
    Session     ("session"),
    Template    ("template");

    private String path;

    private EhrResource(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return path;
    }

    @Override
    public String toString()
    {
        return path;
    }
}
