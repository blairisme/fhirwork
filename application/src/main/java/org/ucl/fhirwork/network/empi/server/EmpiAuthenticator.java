package org.ucl.fhirwork.network.empi.server;

import org.ucl.fhirwork.common.network.Rest.*;
import org.ucl.fhirwork.common.network.exception.AuthenticationException;
import org.ucl.fhirwork.network.empi.data.AuthenticationRequest;

import static org.ucl.fhirwork.common.network.Rest.RestStatusHandlers.throwOnFailureExcept;
import static org.ucl.fhirwork.network.empi.server.EmpiHeader.SessionKey;
import static org.ucl.fhirwork.network.empi.server.EmpiResource.Authenticate;

public class EmpiAuthenticator implements AuthenticationStrategy
{
    private String username;
    private String password;

    public EmpiAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void authenticate(RestServer server, RestRequest request) throws RestException {
        if (server.getHeader(SessionKey) == null) {
            String sessionKey = getSessionKey(server);
            server.setHeader(SessionKey, sessionKey);
            request.setHeader(SessionKey, sessionKey);
        }
    }

    @Override
    public void reauthenticate(RestServer server, RestRequest request) throws RestException {
        server.setHeader(SessionKey, null);
        request.setHeader(SessionKey, null);
        authenticate(server, request);
    }

    private String getSessionKey(RestServer server) throws RestException {
        RestRequest request = server.put(Authenticate);
        request.setBody(new AuthenticationRequest(username, password), AuthenticationRequest.class);

        RestResponse response = request.make(throwOnFailureExcept(401));
        if (response.getStatusCode() == 401) {
            throw new AuthenticationException(server.getAddress(), username);
        }
        return response.asString();
    }
}