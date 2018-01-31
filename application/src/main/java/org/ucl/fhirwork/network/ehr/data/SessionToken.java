package org.ucl.fhirwork.network.ehr.data;

public class SessionToken {
    private String sessionId;

    public SessionToken(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
