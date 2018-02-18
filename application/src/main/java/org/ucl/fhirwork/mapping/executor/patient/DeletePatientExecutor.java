/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping.executor.patient;

import ca.uhn.fhir.model.primitive.IdDt;
import org.ucl.fhirwork.common.framework.ExecutionException;
import org.ucl.fhirwork.common.framework.Executor;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.common.http.RestException;
import org.ucl.fhirwork.mapping.data.PatientFactory;
import org.ucl.fhirwork.network.NetworkService;
import org.ucl.fhirwork.network.empi.data.Person;
import org.ucl.fhirwork.network.empi.server.EmpiServer;
import org.ucl.fhirwork.network.fhir.operations.patient.DeletePatientOperation;

import javax.inject.Inject;

/**
 * Instances of this class convert the delete patient FHIR operation into the
 * appropriate EMPI service calls.
 *
 * @author Blair Butterworth
 */
public class DeletePatientExecutor implements Executor
{
    private String personId;
    private EmpiServer empiServer;
    private PatientFactory patientFactory;

    @Inject
    public DeletePatientExecutor(
            NetworkService networkService,
            PatientFactory patientFactory)
    {
        this.empiServer = networkService.getEmpiServer();
        this.patientFactory = patientFactory;
    }

    @Override
    public void setOperation(Operation operation)
    {
        DeletePatientOperation readPatient = (DeletePatientOperation)operation;
        IdDt patientId = readPatient.getPatientId();
        personId = patientId.getIdPart();
    }

    @Override
    public Object invoke() throws ExecutionException
    {
        try
        {
            empiServer.removePerson(personId);
            return null;
        }
        catch (RestException cause){
            throw new ExecutionException(cause);
        }
    }
}
