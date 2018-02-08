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
import java.util.Map;

import javax.inject.Inject;
import java.util.ArrayList;

public class ReadObservationExecutor implements Executor
{
    private EhrServer ehrServer;
    private QueryService queryService;
    private ObservationFactory observationFactory;
    private EmpiServer empiServer;
    private TokenOrListParam codes;
    private ReferenceParam patient;

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
        TokenOrListParam codes = readObservation.getCodes();
        ReferenceParam patient = readObservation.getPatient();
    }

    @Override
    public Object invoke() throws ExecutionException
    {
        try
        {
            String loinc = "3141-9";
            String patientId = "12345";
            String ehrId = "c831fe4d-0ce9-4a63-8bfa-2c51007f97e5";

            //Instance of empiPerson
            //Person person = empiServer.loadPerson(personId);

            String patientID = patient.getIdPart();

            BaseCodingDt codeElements = codes.getListAsCodings().get(0);

            Map<String, String> codeMap = observationFactory.getCodeMap();

            //String ehrId = ehrServer

            String query = queryService.getQuery(loinc, ehrId);
            QueryBundle bundle = ehrServer.query(query);
            return observationFactory.fromQueryBundle(loinc, patientId, bundle);
        }
        catch (RestException error) {
            throw new ExecutionException(error);
        }
    }
}
