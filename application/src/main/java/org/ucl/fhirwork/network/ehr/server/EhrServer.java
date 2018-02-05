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
import org.ucl.fhirwork.common.serialization.Serializer;
import org.ucl.fhirwork.network.ehr.data.SessionToken;
import org.ucl.fhirwork.network.ehr.data.QueryBundle;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.*;
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

/**
 * Instances of this class represent an EHR server. Methods exists to create,
 * read, update and delete composition and health record data.
 *
 * @author Blair Butterworth
 * @author Xiaolong Chen
 * @author Jiaming Zhou
 */
public class EhrServer
{
    private Provider<RestServer> serverFactory;
    private RestServer server;
    private String address;
    private String username;
    private String password;

    @Inject
    public EhrServer(Provider<RestServer> serverFactory){
        this.serverFactory = serverFactory;
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
        RestRequest request = getServer().get(Query);
        request.setParameters(of(Aql, query));

        RestResponse response = request.make(HandleFailure.ByException);
        return response.asType(QueryBundle.class);
    }

    private RestServer getServer() throws RestException
    {
        if (server == null) {
            String sessionId = getSessionId();
            server = newServer(address, new JsonSerializer(), ImmutableMap.of(ContentType, Json, Accept, Json, SessionId, sessionId));
        }
        return server;
    }

    private String getSessionId() throws RestException
    {
        RestServer rest = newServer(address, new JsonSerializer(), ImmutableMap.of(ContentType, Json, Accept, Json));
        RestRequest request= rest.post(Session).setParameters(ImmutableMap.of(Username, username, Password, password));
        RestResponse response = request.make(HandleFailure.ByException);
        SessionToken sessionToken = response.asType(SessionToken.class);
        return sessionToken.getSessionId();
    }

    private RestServer newServer(String address, Serializer serializer, Map<Object, Object> headers)
    {
        RestServer result = serverFactory.get();
        result.setAddress(address);
        result.setSerializer(serializer);
        result.setHeaders(headers);
        return result;
    }
}

