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


import com.sun.xml.internal.ws.api.server.SDDocument;
import org.ucl.fhirwork.common.http.*;
import org.ucl.fhirwork.common.serialization.JsonSerializer;
import org.ucl.fhirwork.network.ehr.data.SessionToken;




import org.ucl.fhirwork.network.ehr.data.QueryBundle;


import static com.google.common.collect.ImmutableBiMap.of;
import static org.ucl.fhirwork.common.http.HttpHeader.Accept;
import static org.ucl.fhirwork.common.http.HttpHeader.ContentType;
import static org.ucl.fhirwork.common.http.MimeType.Json;
import static org.ucl.fhirwork.network.ehr.server.EhrHeader.SessionId;
import static org.ucl.fhirwork.network.ehr.server.EhrParameter.Aql;
import static org.ucl.fhirwork.network.ehr.server.EhrParameter.Password;
import static org.ucl.fhirwork.network.ehr.server.EhrParameter.Username;
import static org.ucl.fhirwork.network.ehr.server.EhrResource.Query;
import static org.ucl.fhirwork.network.ehr.server.EhrResource.Session;

public class EhrServer {
    private RestServer server;
    private String sessionId;
    private String address;
    private String username;
    private String password;

    public EhrServer()
    {

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



    public QueryBundle query(String query) throws RestException
    {
        RestServer server = getServer();
        RestRequest request = server.get(Query).setParameters(of(Aql, query));
        RestResponse response = request.make(HandleFailure.ByException);
        QueryBundle result = response.asType(QueryBundle.class);
        return result != null ? result : new QueryBundle();

    }



    private RestServer getServer() throws RestException
    {
        if (server == null) {

            String sessionId = getSessionId();
            server = new RestServer(address, new JsonSerializer(), ImmutableMap.of(ContentType, Json, Accept, Json, SessionId, sessionId));
        }
        return server;
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
        RestServer rest = new RestServer(address, new JsonSerializer(), of(ContentType, Json, SessionId, sessionId));
        RestRequest request = rest.delete(Session);
        request.make(HandleFailure.ByException);
    }

}

