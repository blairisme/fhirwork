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
import org.ucl.fhirwork.common.framework.ExecutionException;
import org.ucl.fhirwork.common.framework.Executor;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.mapping.data.ObservationFactory;
import org.ucl.fhirwork.network.NetworkService;
import org.ucl.fhirwork.network.ehr.server.EhrServer;
import org.ucl.fhirwork.network.empi.server.EmpiServer;
import org.ucl.fhirwork.network.fhir.operations.observation.CreateObservationOperation;

import javax.inject.Inject;

/**
 * Instances of this class convert the create obseration FHIR operation into the
 * appropriate EHR and EMPI service calls
 *
 * @autor Alperen Karaoglu
 */
public class CreateObservationExecutor implements Executor {
    private Observation observation;
    private EmpiServer empiServer;
    private EhrServer ehrServer;
    private ObservationFactory observationFactory;
    private ReferenceParam patient;
    private TokenOrListParam tokenList;

    @Inject
    public CreateObservationExecutor(
            NetworkService networkService,
            ObservationFactory observationFactory)
    {
        this.ehrServer = networkService.getEhrServer();
        this.empiServer = networkService.getEmpiServer();
        this.observationFactory = observationFactory;
    }

    @Override
    public void setOperation(Operation operation) {
        CreateObservationOperation createObservation = (CreateObservationOperation)operation;
        observation = createObservation.getObservation();
    }

    // Todo: implement fromObservation() method in observation  factory to convert
    // FHIR observations into EHR observations and addObservation() method in ehrServer
    // to add observation to the EHR instance
    @Override
    public Object invoke() throws ExecutionException
    {
        /*
        try{
            //
            //Observation observationInput = observationFactory.fromObservation(FHIRobservation);
            //Observation EHRobservationOutput = ehrServer.addObservation(observationInput);
            //return FHIRObservation
            return null;
        }
        catch (RestException cause){
            throw new ExecutionException(cause);
        }
        */
        return null;
    }
}
