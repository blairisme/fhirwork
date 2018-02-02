/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */
package org.ucl.fhirwork.mapping.executor;

import ca.uhn.fhir.model.primitive.IdDt;
import org.ucl.fhirwork.common.framework.ExecutionException;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.common.http.RestException;
import org.ucl.fhirwork.mapping.data.PatientFactory;
import org.ucl.fhirwork.network.NetworkService;
import org.ucl.fhirwork.network.empi.data.Person;
import org.ucl.fhirwork.network.empi.server.EmpiServer;
import org.ucl.fhirwork.network.fhir.operations.patient.ReadPatientOperation;
import javax.inject.Inject;

public class SearchPatientExecutor {
    private String personId;
    private EmpiServer empiServer;
    private PatientFactory patientFactory;

    @Inject
    public SearchPatientExecutor(
            NetworkService networkService,
            PatientFactory patientFactory
    )
    {
        this.empiServer = networkService.getEmpiServer();
        this.patientFactory = patientFactory;
    }

    @Override
    public void setOperation(Operation operation){
        ReadPatientOperation readPatient = (ReadPatientOperation)operation;
        IdDt patientId = readPatient.getPatientId();
        personId = patientId.getIdPart();
    }

    /**
     * If a patient is found we return it, otherwise we return a null object
     */
    @Override
    public Object invoke() throws ExecutionException
    {
        try
        {
             if(empiServer.personExists(personId)){
                 Person personOutput = empiServer.loadPerson((personId));
                 return patientFactory.fromPerson(personOutput);
             }else{
                 return null;
             }
        }
        catch (RestException cause){
            throw new ExecutionException(cause);
        }

    }
}
