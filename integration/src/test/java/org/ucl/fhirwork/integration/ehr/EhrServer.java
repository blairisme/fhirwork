package org.ucl.fhirwork.integration.ehr;

import static org.ucl.fhirwork.integration.ehr.EhrEndpoint.Ehr;
import static org.ucl.fhirwork.integration.ehr.EhrEndpoint.Session;
import static org.ucl.fhirwork.integration.ehr.EhrHeader.*;
import static org.ucl.fhirwork.integration.ehr.EhrParameter.*;
import static org.ucl.fhirwork.integration.common.http.HttpHeader.*;
import static org.ucl.fhirwork.integration.common.http.MimeType.*;

import com.google.common.collect.ImmutableMap;
import org.ucl.fhirwork.integration.common.http.RestServer;
import org.ucl.fhirwork.integration.common.http.RestServerException;
import org.ucl.fhirwork.integration.common.serialization.JsonSerializer;
import org.ucl.fhirwork.integration.common.serialization.Serializer;
import org.ucl.fhirwork.integration.common.serialization.XmlSerializer;
import org.ucl.fhirwork.integration.ehr.model.*;

import java.io.IOException;
import java.util.*;

public class EhrServer
{
    private RestServer restServer;
    private String sessionId;
    private String address;
    private String username;
    private String password;

    public EhrServer(String address, String username, String password)
    {
        this.address = address;
        this.username = username;
        this.password = password;
    }

    public void addTemplate(TemplateReference template) throws IOException, RestServerException
    {
        RestServer server = createServer(ImmutableMap.of(ContentType, Xml), new XmlSerializer());
        server.post(EhrEndpoint.Template, template.getContent(), Collections.emptyMap());
    }

    public List<Template> getTemplates() throws RestServerException
    {
        RestServer server = getServer();
        Templates result = server.get(EhrEndpoint.Template, Templates.class, Collections.emptyMap());
        return result.getTemplates();
    }

    public boolean templateExists(String templateId) throws RestServerException
    {
        for (Template template: getTemplates()){
            if (Objects.equals(template.getTemplateId(), templateId)){
                return true;
            }
        }
        return false;
    }

    public HealthRecord createEhr(String id, String namespace) throws RestServerException
    {
        RestServer server = getServer();
        return server.post(Ehr, HealthRecord.class, ImmutableMap.of(SubjectId, id, SubjectNamespace, namespace));
    }

    public HealthRecord getEhr(String id, String namespace) throws RestServerException
    {
        RestServer server = getServer();
        return server.get(Ehr, HealthRecord.class, ImmutableMap.of(SubjectId, id, SubjectNamespace, namespace));
    }

    public boolean ehrExists(String id, String namespace) throws RestServerException
    {
        RestServer server = getServer();
        return server.testGet(Ehr, ImmutableMap.of(SubjectId, id, SubjectNamespace, namespace), 200);
    }

    public QueryBundle query(String query) throws RestServerException
    {
        RestServer server = getServer();
        QueryBundle result = server.get(EhrEndpoint.Query, QueryBundle.class, ImmutableMap.of(Aql, query));
        return result != null ? result : new QueryBundle();
    }

    public <T extends FlatComposition> void createComposition(HealthRecord record, T composition, Class<T> type) throws RestServerException
    {
        RestServer server = getServer();
        Map parameters = ImmutableMap.of(EhrId, record.getEhrId(), TemplateId, composition.getCompositionId(), Format, "FLAT");
        server.post(EhrEndpoint.Composition, composition, type, parameters);
    }

    public List<Composition> getCompositions(String ehrId) throws RestServerException
    {
        List<Composition> compositions = new ArrayList<>();
        QueryBundle bundle = query("select a from EHR [ehr_id/value='" + ehrId + "'] contains COMPOSITION a");

        for (QueryResult queryResult: bundle.getResultSet()) {
            compositions.add(queryResult.getComposition());
        }
        return compositions;
    }

    public void removeComposition(Composition composition) throws RestServerException
    {
        RestServer server = getServer();
        server.delete("composition/" + composition.getUid().getValue(), Collections.emptyMap());
    }

    public boolean ping()
    {
        try{
            getTemplates();
            return true;
        }
        catch (RestServerException error){
            return false;
        }
    }

    private RestServer getServer() throws RestServerException
    {
        if (restServer == null){
            restServer = createServer(ImmutableMap.of(ContentType, Json, Accept, Json), new JsonSerializer());
        }
        return restServer;
    }

    private RestServer createServer(Map<Object, Object> headers, Serializer serializer) throws RestServerException
    {
        Map<Object, Object> authHeaders = new HashMap<>();
        authHeaders.putAll(headers);
        authHeaders.put(SessionId, getSessionId());
        return new RestServer(address, serializer, authHeaders);
    }

    private String getSessionId() throws RestServerException
    {
        if (sessionId == null) {
            RestServer rest = new RestServer(address, new JsonSerializer(), ImmutableMap.of(ContentType, Json, Accept, Json));
            SessionToken session = rest.post(Session, SessionToken.class, ImmutableMap.of(Username, username, Password, password));
            sessionId = session.getSessionId();
        }
        return sessionId;
    }
}
