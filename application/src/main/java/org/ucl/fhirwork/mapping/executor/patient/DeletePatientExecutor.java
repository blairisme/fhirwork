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
import org.apache.commons.lang3.Validate;
import org.ucl.fhirwork.common.framework.ExecutionException;
import org.ucl.fhirwork.common.framework.Executor;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.mapping.data.InternalIdentifierFactory;
import org.ucl.fhirwork.mapping.data.PatientFactory;
import org.ucl.fhirwork.network.NetworkService;
import org.ucl.fhirwork.network.empi.data.InternalIdentifier;
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
    private EmpiServer empiServer;
    private InternalIdentifier personId;
    private InternalIdentifierFactory identifierFactory;

    @Inject
    public DeletePatientExecutor(
        NetworkService networkService,
        InternalIdentifierFactory identifierFactory)
    {
        this.empiServer = networkService.getEmpiServer();
        this.identifierFactory = identifierFactory;
    }

    @Override
    public void setOperation(Operation operation)
    {
        Validate.notNull(operation);
        DeletePatientOperation readPatient = (DeletePatientOperation)operation;
        personId = identifierFactory.fromId(readPatient.getPatientId());
    }

    @Override
    public Object invoke() throws ExecutionException
    {
        try {
            empiServer.removePerson(personId);
            return null;
        }
        catch (Throwable cause) {
            throw new ExecutionException(cause);
        }
    }
}
