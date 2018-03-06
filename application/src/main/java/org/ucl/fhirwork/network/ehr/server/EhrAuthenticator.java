package org.ucl.fhirwork.network.ehr.server;

import org.ucl.fhirwork.common.network.Rest.*;
import org.ucl.fhirwork.common.network.exception.AuthenticationException;
import org.ucl.fhirwork.network.ehr.data.SessionToken;
import org.ucl.fhirwork.network.empi.data.AuthenticationRequest;

import static com.google.common.collect.ImmutableMap.of;
import static org.ucl.fhirwork.common.network.Rest.RestStatusHandlers.throwOnFailureExcept;
import static org.ucl.fhirwork.network.ehr.server.EhrHeader.SessionId;
import static org.ucl.fhirwork.network.ehr.server.EhrParameter.Password;
import static org.ucl.fhirwork.network.ehr.server.EhrParameter.Username;
import static org.ucl.fhirwork.network.ehr.server.EhrResource.Session;

public class EhrAuthenticator implements AuthenticationStrategy
{
    private String username;
    private String password;

    public EhrAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void authenticate(RestServer server, RestRequest request) throws RestException {
        if (server.getHeader(SessionId) == null) {
            SessionToken sessionToken = getSessionToken(server);
            server.setHeader(SessionId, sessionToken.getSessionId());
            request.setHeader(SessionId, sessionToken.getSessionId());
        }
    }

    @Override
    public void reauthenticate(RestServer server, RestRequest request) throws RestException {
        server.setHeader(SessionId, null);
        request.setHeader(SessionId, null);
        authenticate(server, request);
    }

    private SessionToken getSessionToken(RestServer server) throws RestException {
        RestRequest request = server.post(Session);
        request.setParameters(of(Username, username, Password, password));
        request.setBody(new AuthenticationRequest(username, password), AuthenticationRequest.class);

        RestResponse response = request.make(throwOnFailureExcept(401));
        if (response.getStatusCode() == 401) {
            throw new AuthenticationException(server.getAddress(), username);
        }
        return response.asType(SessionToken.class);
    }
}