/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping.executor.observation;

import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.TokenOrListParam;
import org.apache.commons.lang3.Validate;
import org.ucl.fhirwork.common.framework.ExecutionException;
import org.ucl.fhirwork.common.framework.Executor;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.common.http.RestException;
import org.ucl.fhirwork.configuration.ConfigService;
import org.ucl.fhirwork.configuration.data.ConfigType;
import org.ucl.fhirwork.configuration.data.GeneralConfig;
import org.ucl.fhirwork.mapping.data.ObservationFactory;
import org.ucl.fhirwork.mapping.query.QueryService;
import org.ucl.fhirwork.network.NetworkService;
import org.ucl.fhirwork.network.ehr.data.HealthRecord;
import org.ucl.fhirwork.network.ehr.data.ObservationBundle;
import org.ucl.fhirwork.network.ehr.data.QueryBundle;
import org.ucl.fhirwork.network.ehr.server.EhrServer;
import org.ucl.fhirwork.network.empi.data.Identifier;
import org.ucl.fhirwork.network.empi.data.Person;
import org.ucl.fhirwork.network.empi.data.PersonUtils;
import org.ucl.fhirwork.network.empi.server.EmpiServer;
import org.ucl.fhirwork.network.fhir.data.TokenListUtils;
import org.ucl.fhirwork.network.fhir.data.TokenSystem;
import org.ucl.fhirwork.network.fhir.operations.observation.ReadObservationOperation;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.ucl.fhirwork.network.fhir.data.TokenListUtils.getCodeElements;

/**
 * Instances of this class convert the read observation FHIR operation into the
 * appropriate EHR and EMPI service calls.
 *
 * @author Blair Butterworth
 * @author Alperen Karaoglu
 */
public class ReadObservationExecutor implements Executor
{
    private EhrServer ehrServer;
    private EmpiServer empiServer;
    private QueryService queryService;
    private ConfigService configService;
    private ObservationFactory observationFactory;
    private ReferenceParam patient;
    private TokenOrListParam tokenList;

    @Inject
    public ReadObservationExecutor(
        NetworkService networkService,
        ConfigService configService,
        QueryService queryService,
        ObservationFactory observationFactory)
    {
        this.ehrServer = networkService.getEhrServer();
        this.empiServer = networkService.getEmpiServer();
        this.queryService = queryService;
        this.configService = configService;
        this.observationFactory = observationFactory;
    }

    @Override
    public void setOperation(Operation operation) {
        Validate.notNull(operation);
        Validate.isInstanceOf(ReadObservationOperation.class, operation);

        ReadObservationOperation readObservation = (ReadObservationOperation)operation;
        tokenList = readObservation.getCodes();
        patient = readObservation.getPatient();
    }

    @Override
    public Object invoke() throws ExecutionException
    {
        try {
            Validate.notNull(tokenList);
            Validate.notNull(patient);

            String patientId = patient.getIdPart();
            String ehrId = getEhrId(patientId);
            return getObservations(tokenList, ehrId, patientId);
        }
        catch (Throwable error) {
            throw new ExecutionException(error);
        }
    }

    private String getEhrId(String patientId) throws RestException
    {
        String ehrIdSystem = getEhrIdSystem();
        Person person = empiServer.loadPerson(patientId);
        Identifier personId = PersonUtils.getIdentifier(person, ehrIdSystem);
        HealthRecord record  = ehrServer.getEhr(personId.getIdentifier(), ehrIdSystem);
        return record.getEhrId();
    }

    private String getEhrIdSystem()
    {
        GeneralConfig generalConfig = configService.getConfig(ConfigType.General);
        return generalConfig.getEhrIdSystem();
    }

    private List<Observation> getObservations(TokenOrListParam tokenList, String ehrId, String patientId) throws RestException
    {
        if (! tokenList.getListAsCodings().isEmpty()){
            return getObservations(getCodeElements(tokenList, TokenSystem.Loinc), ehrId, patientId);
        }
        return getAllObservations(ehrId, patientId);
    }

    private List<Observation> getAllObservations(String ehrId, String patientId) throws RestException
    {
        List<Observation> result = new ArrayList<>();
        for (String code: queryService.getSupported()) {
            result.addAll(getObservations(code, ehrId, patientId));
        }
        return result;
    }

    private List<Observation> getObservations(List<String> codes, String ehrId, String patientId) throws RestException
    {
        List<Observation> result = new ArrayList<>();
        for (String code: codes) {
            result.addAll(getObservations(code, ehrId, patientId));
        }
        return result;
    }

    private List<Observation> getObservations(String code, String ehrId, String patientId) throws RestException
    {
        if (queryService.isSupported(code)) {
            String query = queryService.getQuery(code, ehrId);
            ObservationBundle bundle = ehrServer.query(query, ObservationBundle.class);
            return observationFactory.fromQueryBundle(code, patientId, bundle);
        }
        return Collections.emptyList();
    }
}
