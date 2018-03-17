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
import org.ucl.fhirwork.common.network.exception.ResourceMissingException;
import org.ucl.fhirwork.common.reflect.TypeUtils;
import org.ucl.fhirwork.common.serialization.JsonSerializer;
import org.ucl.fhirwork.network.ehr.data.HealthRecord;
import org.ucl.fhirwork.network.ehr.data.QueryBundle;

import javax.inject.Inject;
import javax.inject.Provider;

import static com.google.common.collect.ImmutableMap.of;
import static org.ucl.fhirwork.common.network.Rest.RestStatusStrategies.throwOnFailure;
import static org.ucl.fhirwork.common.network.Rest.RestStatusStrategies.throwOnFailureExcept;
import static org.ucl.fhirwork.common.network.http.HttpHeader.Accept;
import static org.ucl.fhirwork.common.network.http.HttpHeader.ContentType;
import static org.ucl.fhirwork.common.network.http.MimeType.Json;
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
    private AuthenticatedRestServer server;
    private Provider<AuthenticatedRestServer> serverFactory;

    private String address;
    private String username;
    private String password;

    @Inject
    public BasicEhrServer(Provider<AuthenticatedRestServer> serverFactory)
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
    public HealthRecord getHealthRecord(String id, String namespace) throws RestException
    {
        RestRequest request = getServer().get(Ehr);
        request.setParameters(ImmutableMap.of(SubjectId, id, SubjectNamespace, namespace));
        request.setStatusStrategy(throwOnFailureExcept(404));
        RestResponse response = request.make();

        if (response.hasStatusCode(404)){
            throw new ResourceMissingException("EHR", id);
        }
        return response.asType(HealthRecord.class);
    }

    @Override
    public <T extends QueryBundle> T query(String query, Class<T> type) throws RestException
    {
        RestRequest request = getServer().get(Query);
        request.setParameters(of(Aql, query));
        request.setStatusStrategy(throwOnFailure());

        RestResponse response = request.make();
        return response.getStatusCode() != 204 ? response.asType(type) : TypeUtils.newInstance(type);
    }

    private synchronized RestServer getServer()
    {
        if (server == null) {
            server = serverFactory.get();
            server.setAddress(address);
            server.setSerializer(new JsonSerializer());
            server.setHeaders(of(ContentType, Json, Accept, Json));
            server.setAuthenticationStrategy(new EhrAuthenticator(username, password));
        }
        return server;
    }
}

