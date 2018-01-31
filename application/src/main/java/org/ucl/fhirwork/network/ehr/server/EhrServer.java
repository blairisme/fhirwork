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

import com.google.common.collect.ImmutableMap;

import org.ucl.fhirwork.common.http.*;
import org.ucl.fhirwork.common.serialization.JsonSerializer;
import org.ucl.fhirwork.network.ehr.data.SessionToken;
import static org.ucl.fhirwork.common.http.HttpHeader.*;
import static org.ucl.fhirwork.common.http.MimeType.*;
import static org.ucl.fhirwork.network.ehr.server.EhrResource.*;
import static org.ucl.fhirwork.network.ehr.server.EhrParameter.*;

/**
 * Instances of this class represent an EHR server. Methods exists to create,
 * read, update and delete health records.
 *
 * @author Blair Butterworth
 */
public class EhrServer {
    private RestServer restServer;
    private String sessionId;
    private String address;
    private String username;
    private String password;

    public EhrServer() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSessionId() throws RestException {
        if (sessionId == null) {
            RestServer rest = new RestServer(address, new JsonSerializer(), ImmutableMap.of(ContentType, Json, Accept, Json));
            RestRequest request= rest.post(Session).setParameters(ImmutableMap.of(Username, username, Password, password));
            RestResponse response = request.make(HandleFailure.ByException);
            SessionToken sessionToken = response.asType(SessionToken.class);
            sessionId = sessionToken.getSessionId();
        }
        return sessionId;
    }
    public void deleteSessionId() throws RestException {
        RestServer rest = new RestServer(address, new JsonSerializer(),ImmutableMap.of(ContentType, Json, SessionId, sessionId));
        RestRequest request = rest.delete(Session);
        request.make(HandleFailure.ByException);
    }
}

