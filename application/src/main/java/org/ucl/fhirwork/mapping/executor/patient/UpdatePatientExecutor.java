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

import ca.uhn.fhir.model.dstu2.resource.Patient;
import org.ucl.fhirwork.common.framework.ExecutionException;
import org.ucl.fhirwork.common.framework.Executor;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.mapping.data.InternalIdentifierFactory;
import org.ucl.fhirwork.mapping.data.PatientFactory;
import org.ucl.fhirwork.mapping.data.PersonFactory;
import org.ucl.fhirwork.network.NetworkService;
import org.ucl.fhirwork.network.empi.data.InternalIdentifier;
import org.ucl.fhirwork.network.empi.data.Person;
import org.ucl.fhirwork.network.empi.server.EmpiServer;
import org.ucl.fhirwork.network.fhir.operations.patient.UpdatePatientOperation;

import javax.inject.Inject;

/**
 * Instances of this class convert the update patient FHIR operation into the
 * appropriate EMPI service calls.
 *
 * @author Blair Butterworth
 */
public class UpdatePatientExecutor implements Executor
{
    private Patient patient;
    private InternalIdentifier patientId;
    private EmpiServer empiServer;
    private PatientFactory patientFactory;
    private PersonFactory personFactory;
    private InternalIdentifierFactory identifierFactory;

    @Inject
    public UpdatePatientExecutor(
        NetworkService networkService,
        PatientFactory patientFactory,
        PersonFactory personFactory,
        InternalIdentifierFactory identifierFactory)
    {
        this.empiServer = networkService.getEmpiServer();
        this.patientFactory = patientFactory;
        this.personFactory = personFactory;
        this.identifierFactory = identifierFactory;
    }

    @Override
    public void setOperation(Operation operation)
    {
        UpdatePatientOperation updatePatient = (UpdatePatientOperation)operation;
        patient = updatePatient.getPatient();
        patientId = identifierFactory.fromId(patient.getId());
    }

    @Override
    public Object invoke() throws ExecutionException
    {
        try
        {
            Person currentPerson = empiServer.loadPerson(patientId);
            Person newPerson = personFactory.update(currentPerson, patient);
            Person updatedPerson = empiServer.updatePerson(newPerson);
            return patientFactory.fromPerson(updatedPerson);
        }
        catch (Throwable cause) {
            throw new ExecutionException(cause);
        }
    }
}
