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
import org.ucl.fhirwork.common.reflect.TypeUtils;
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
import static org.ucl.fhirwork.network.ehr.server.EhrParameter.*;
import static org.ucl.fhirwork.network.ehr.server.EhrResource.*;
import org.ucl.fhirwork.network.ehr.data.*;
import org.ucl.fhirwork.network.ehr.exception.MissingHealthRecordException;

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
    public EhrServer(Provider<RestServer> serverFactory)
    {
        this.serverFactory = serverFactory;
    }

    /**
     * Sets the address and authentication information used to connect to the
     * EHR server.
     *
     * @param address   the URL of an EHR server.
     * @param username  the name of an account on the EMPI server.
     * @param password  the password of an EMPI account.
     */
    public synchronized void setConnectionDetails(String address, String username, String password)
    {
        this.address = address;
        this.username = username;
        this.password = password;
        this.server = null;
    }

    public HealthRecord getEhr(String id, String namespace) throws RestException, MissingHealthRecordException
    {
        RestRequest request = getServer().get(Ehr);
        request.setParameters(ImmutableMap.of(SubjectId, id, SubjectNamespace, namespace));

        RestResponse response = request.make(HandleFailure.ByException);
        return response.asType(HealthRecord.class);
    }

    public <T extends QueryBundle> T query(String query, Class<T> type) throws RestException
    {
        RestRequest request = getServer().get(Query);
        request.setParameters(of(Aql, query));

        RestResponse response = request.make(HandleFailure.ByException);
        return response.getStatusCode() != 204 ? response.asType(type) : TypeUtils.newInstance(type);
    }

    private synchronized RestServer getServer() throws RestException
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
        RestRequest request = rest.post(Session).setParameters(ImmutableMap.of(Username, username, Password, password));

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

