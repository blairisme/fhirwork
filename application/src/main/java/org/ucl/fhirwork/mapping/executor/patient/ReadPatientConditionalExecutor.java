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

import org.ucl.fhirwork.common.framework.ExecutionException;
import org.ucl.fhirwork.common.framework.Executor;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.common.network.Rest.RestException;
import org.ucl.fhirwork.mapping.data.PatientFactory;
import org.ucl.fhirwork.mapping.data.PersonFactory;
import org.ucl.fhirwork.network.NetworkService;
import org.ucl.fhirwork.network.empi.data.Person;
import org.ucl.fhirwork.network.empi.server.EmpiServer;
import org.ucl.fhirwork.network.fhir.data.SearchParameter;
import org.ucl.fhirwork.network.fhir.operations.patient.ReadPatientOperation;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;

/**
 * Instances of this class convert the patient search FHIR operation into the
 * appropriate EMPI service calls.
 *
 * @author Alperen Karaoglu
 * @author Blair Butterworth
 */
public class ReadPatientConditionalExecutor implements Executor
{
    private Map<SearchParameter, Object> searchParameters;
    private EmpiServer empiServer;
    private PatientFactory patientFactory;
    private PersonFactory personFactory;

    @Inject
    public ReadPatientConditionalExecutor(
            NetworkService networkService,
            PatientFactory patientFactory,
            PersonFactory personFactory)
    {
        this.empiServer = networkService.getEmpiServer();
        this.patientFactory = patientFactory;
        this.personFactory = personFactory;
    }

    @Override
    public void setOperation(Operation operation){
        ReadPatientOperation readPatient = (ReadPatientOperation)operation;
        searchParameters = readPatient.getSearchParameters();
    }

    @Override
    public Object invoke() throws ExecutionException
    {
        try
        {
            Collection<Person> people = findPeople(searchParameters);
            return patientFactory.fromPeople(people);
        }
        catch (Throwable cause){
            throw new ExecutionException(cause);
        }
    }

    private Collection<Person> findPeople(Map<SearchParameter, Object> searchParameters) throws RestException
    {
        if (! searchParameters.isEmpty()){
            Person template = personFactory.fromSearchParameters(searchParameters);
            return empiServer.findPersons(template);
        }
        return empiServer.loadAllPersons(0, 100); //TODO: Paging mechanism needed. Is included in FHIR spec
    }
}
