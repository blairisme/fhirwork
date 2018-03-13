/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.empi.server;

import org.ucl.fhirwork.common.network.Rest.*;
import org.ucl.fhirwork.common.network.exception.AuthenticationException;
import org.ucl.fhirwork.network.empi.data.AuthenticationRequest;

import static org.ucl.fhirwork.common.network.Rest.RestStatusStrategies.throwOnFailureExcept;
import static org.ucl.fhirwork.network.empi.server.EmpiHeader.SessionKey;
import static org.ucl.fhirwork.network.empi.server.EmpiResource.Authenticate;

/**
 * Implementors of this class provide an authentication strategy for
 * authenticating to an EMPI server.
 *
 * @author Blair Butterworth
 */
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
        request.setStatusStrategy(throwOnFailureExcept(401));

        RestResponse response = request.make();
        if (response.getStatusCode() == 401) {
            throw new AuthenticationException(server.getAddress(), username);
        }
        return response.asString();
    }
}