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
import org.ucl.fhirwork.common.framework.Executor;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.common.http.RestException;
import org.ucl.fhirwork.mapping.data.PatientFactory;
import org.ucl.fhirwork.mapping.data.PersonFactory;
import org.ucl.fhirwork.network.NetworkService;
import org.ucl.fhirwork.network.empi.data.Person;
import org.ucl.fhirwork.network.empi.server.EmpiServer;
import org.ucl.fhirwork.network.fhir.operations.ReadPatientOperation;

import javax.inject.Inject;

public class ReadPatientExecutor implements Executor
{
    private IdDt identifier;
    private EmpiServer empiServer;
    private PatientFactory patientFactory;
    private PersonFactory personFactory;

    @Inject
    public ReadPatientExecutor(
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
        ReadPatientOperation readPatient = (ReadPatientOperation)operation;
        identifier = readPatient.getPatientId();
    }

    @Override
    public Object invoke() throws ExecutionException
    {
        try {
            Person personInput = personFactory.fromId(identifier);
            Person personOutput = empiServer.addPerson(personInput);
            return patientFactory.newPatient(personOutput);
        }
        catch (RestException cause){
            throw new ExecutionException(cause);
        }
    }
}
