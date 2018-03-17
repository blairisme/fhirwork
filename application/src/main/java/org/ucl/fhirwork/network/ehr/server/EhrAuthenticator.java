/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.ehr.server;

import org.ucl.fhirwork.common.network.Rest.*;
import org.ucl.fhirwork.common.network.exception.AuthenticationException;
import org.ucl.fhirwork.network.ehr.data.SessionToken;
import org.ucl.fhirwork.network.empi.data.AuthenticationRequest;

import static com.google.common.collect.ImmutableMap.of;
import static org.ucl.fhirwork.common.network.Rest.RestStatusStrategies.throwOnFailureExcept;
import static org.ucl.fhirwork.network.ehr.server.EhrHeader.SessionId;
import static org.ucl.fhirwork.network.ehr.server.EhrParameter.Password;
import static org.ucl.fhirwork.network.ehr.server.EhrParameter.Username;
import static org.ucl.fhirwork.network.ehr.server.EhrResource.Session;

/**
 * Implementors of this class provide an authentication strategy for
 * authenticating to an EHR server.
 *
 * @author Blair Butterworth
 */
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
        request.setStatusStrategy(throwOnFailureExcept(401));

        RestResponse response = request.make();
        if (response.getStatusCode() == 401) {
            throw new AuthenticationException(server.getAddress(), username);
        }
        return response.asType(SessionToken.class);
    }
}