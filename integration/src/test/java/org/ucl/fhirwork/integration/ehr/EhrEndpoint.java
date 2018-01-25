package org.ucl.fhirwork.integration.ehr;

import org.ucl.fhirwork.integration.common.http.RestEndpoint;

public enum EhrEndpoint implements RestEndpoint
{
    Composition ("composition"),
    Ehr         ("ehr"),
    Observation ("observation"),
    Query       ("query"),
    Session     ("session"),
    Template    ("template");

    private String path;

    private EhrEndpoint(String path)
    {
        this.path = path;
    }

    @Override
    public String getPath()
    {
        return path;
    }
}
