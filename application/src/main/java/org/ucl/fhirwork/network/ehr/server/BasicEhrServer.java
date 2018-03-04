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
import org.ucl.fhirwork.common.network.Rest.*;
import org.ucl.fhirwork.common.network.exception.AuthenticationException;
import org.ucl.fhirwork.common.reflect.TypeUtils;
import org.ucl.fhirwork.common.serialization.JsonSerializer;
import org.ucl.fhirwork.common.serialization.Serializer;
import org.ucl.fhirwork.network.ehr.data.HealthRecord;
import org.ucl.fhirwork.network.ehr.data.QueryBundle;
import org.ucl.fhirwork.network.ehr.data.SessionToken;
import org.ucl.fhirwork.network.ehr.exception.MissingRecordException;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Map;

import static com.google.common.collect.ImmutableMap.of;
import static org.ucl.fhirwork.common.network.Rest.RestStatusHandlers.throwOnFailedStatus;
import static org.ucl.fhirwork.common.network.Rest.RestStatusHandlers.throwOnFailureExcept;
import static org.ucl.fhirwork.common.network.http.HttpHeader.Accept;
import static org.ucl.fhirwork.common.network.http.HttpHeader.ContentType;
import static org.ucl.fhirwork.common.network.http.MimeType.Json;
import static org.ucl.fhirwork.network.ehr.server.EhrHeader.SessionId;
import static org.ucl.fhirwork.network.ehr.server.EhrParameter.*;
import static org.ucl.fhirwork.network.ehr.server.EhrResource.*;

/**
 * Instances of this class represent an EHR server. Methods exists to create,
 * read, update and delete composition and health record data.
 *
 * @author Blair Butterworth
 * @author Xiaolong Chen
 * @author Jiaming Zhou
 */
public class BasicEhrServer implements EhrServer
{
    private Provider<RestServer> serverFactory;
    private RestServer server;
    private String address;
    private String username;
    private String password;

    @Inject
    public BasicEhrServer(Provider<RestServer> serverFactory)
    {
        this.serverFactory = serverFactory;
    }

    @Override
    public synchronized void setConnectionDetails(String address, String username, String password)
    {
        this.address = address;
        this.username = username;
        this.password = password;
        this.server = null;
    }

    @Override
    public HealthRecord getHealthRecord(String id, String namespace) throws RestException, MissingRecordException
    {
        RestRequest request = getServer().get(Ehr);
        request.setParameters(ImmutableMap.of(SubjectId, id, SubjectNamespace, namespace));

        RestResponse response = request.make(throwOnFailedStatus());
        return response.asType(HealthRecord.class);
    }

    @Override
    public <T extends QueryBundle> T query(String query, Class<T> type) throws RestException
    {
        RestRequest request = getServer().get(Query);
        request.setParameters(of(Aql, query));

        RestResponse response = request.make(throwOnFailedStatus());
        return response.getStatusCode() != 204 ? response.asType(type) : TypeUtils.newInstance(type);
    }

    private synchronized RestServer getServer() throws RestException
    {
        if (server == null) {
            String sessionId = getSessionId();
            server = newServer(address, new JsonSerializer(), of(ContentType, Json, Accept, Json, SessionId, sessionId));
        }
        return server;
    }

    private String getSessionId() throws RestException
    {
        RestServer rest = newServer(address, new JsonSerializer(), of(ContentType, Json, Accept, Json));
        RestRequest request = rest.post(Session).setParameters(of(Username, username, Password, password));

        RestResponse response = request.make(throwOnFailureExcept(401));
        if (response.getStatusCode() == 401) {
            throw new AuthenticationException(address, username);
        }
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

