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
import org.apache.commons.lang3.Validate;
import org.ucl.fhirwork.common.framework.ExecutionException;
import org.ucl.fhirwork.common.framework.Executor;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.mapping.data.PatientFactory;
import org.ucl.fhirwork.mapping.data.PersonFactory;
import org.ucl.fhirwork.network.NetworkService;
import org.ucl.fhirwork.network.empi.data.Person;
import org.ucl.fhirwork.network.empi.server.EmpiServer;
import org.ucl.fhirwork.network.fhir.data.SearchParameter;
import org.ucl.fhirwork.network.fhir.operations.patient.UpdatePatientOperation;

import javax.inject.Inject;
import java.util.Map;

/**
 * Instances of this class convert the conditional update patient FHIR
 * operation into the appropriate EMPI service calls.
 *
 * @author Blair Butterworth
 */
public class UpdatePatientConditionalExecutor  implements Executor
{
    private Patient patient;
    private Map<SearchParameter, Object> parameters;
    private EmpiServer empiServer;
    private PatientFactory patientFactory;
    private PersonFactory personFactory;

    @Inject
    public UpdatePatientConditionalExecutor(
            NetworkService networkService,
            PatientFactory patientFactory,
            PersonFactory personFactory)
    {
        this.empiServer = networkService.getEmpiServer();
        this.patientFactory = patientFactory;
        this.personFactory = personFactory;
    }

    @Override
    public void setOperation(Operation operation)
    {
        Validate.notNull(operation);
        UpdatePatientOperation updatePatient = (UpdatePatientOperation)operation;
        patient = updatePatient.getPatient();
        parameters = updatePatient.getSearchParameters();
    }

    @Override
    public Object invoke() throws ExecutionException
    {
        try {
            Person searchTemplate = personFactory.fromSearchParameters(parameters);
            Person currentPerson = empiServer.findPerson(searchTemplate);
            Person newPerson = personFactory.update(currentPerson, patient);
            Person updatedPerson = empiServer.updatePerson(newPerson);
            return patientFactory.fromPerson(updatedPerson);
        }
        catch (Throwable error) {
            throw new ExecutionException(error);
        }
    }
}
