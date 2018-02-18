package org.ucl.fhirwork.integration.ehr.model;

public class SessionToken
{
    private String sessionId;

    public SessionToken(String sessionId)
    {
        this.sessionId = sessionId;
    }

    public String getSessionId()
    {
        return sessionId;
    }
}
