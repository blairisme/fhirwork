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

import ca.uhn.fhir.model.base.composite.BaseCodingDt;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.primitive.CodeDt;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.TokenOrListParam;
import org.ucl.fhirwork.network.empi.data.Person;
import org.ucl.fhirwork.common.framework.ExecutionException;
import ca.uhn.fhir.model.primitive.IdDt;
import org.ucl.fhirwork.common.framework.Executor;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.common.http.RestException;
import org.ucl.fhirwork.mapping.data.ObservationFactory;
import org.ucl.fhirwork.mapping.data.PatientFactory;
import org.ucl.fhirwork.mapping.query.QueryService;
import org.ucl.fhirwork.network.NetworkService;
import org.ucl.fhirwork.network.ehr.data.QueryBundle;
import org.ucl.fhirwork.network.ehr.server.EhrServer;
import org.ucl.fhirwork.network.empi.server.EmpiServer;
import org.ucl.fhirwork.network.fhir.operations.observation.ReadObservationOperation;

import java.util.*;

import javax.inject.Inject;

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
    private QueryService queryService;
    private ObservationFactory observationFactory;
    private EmpiServer empiServer;
    private ReferenceParam patient;
    private TokenOrListParam tokenList;

    @Inject
    public ReadObservationExecutor(
            NetworkService networkService,
            QueryService queryService,
            ObservationFactory observationFactory)
    {
        this.ehrServer = networkService.getEhrServer();
        this.empiServer = networkService.getEmpiServer();
        this.queryService = queryService;
        this.observationFactory = observationFactory;
    }

    @Override
    public void setOperation(Operation operation) {
        ReadObservationOperation readObservation = (ReadObservationOperation)operation;
        tokenList = readObservation.getCodes();
        patient = readObservation.getPatient();
    }

    @Override
    public Object invoke() throws ExecutionException
    {
        try
        {
            String patientId = patient.getIdPart();
            String ehrId = getEhrId(patientId);
            List<String> codes = getCodes(tokenList);
            return getObservations(codes, ehrId, patientId);
        }
        catch (Throwable error){
            throw new ExecutionException(error);
        }
    }

    // Todo: Determine ehrId from empi and ehr services
    private String getEhrId(String patientId)
    {
        return "c831fe4d-0ce9-4a63-8bfa-2c51007f97e5";
    }

    private List<String> getCodes(TokenOrListParam codes)
    {
        List<String> result = new ArrayList<>();
        for (BaseCodingDt coding: codes.getListAsCodings()){
            result.add(coding.getCodeElement().getValue());
        }
        return result;
    }

    private List<Observation> getObservations(List<String> codes, String ehrId, String patientId) throws RestException
    {
        List<Observation> result = new ArrayList<>();
        for (String code: codes){
            result.addAll(getObservations(code, ehrId, patientId));
        }
        return result;
    }

    private List<Observation> getObservations(String code, String ehrId, String patientId) throws RestException
    {
        if (queryService.isSupported(code)) {
            String query = queryService.getQuery(code, ehrId);
            QueryBundle bundle = ehrServer.query(query);
            return observationFactory.fromQueryBundle(code, patientId, bundle);
        }
        return Collections.emptyList(); //Todo: Should we throw a misconfigured exception?
    }
}
