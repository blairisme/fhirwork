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

import ca.uhn.fhir.model.dstu2.resource.Patient;
import org.ucl.fhirwork.common.framework.ExecutionException;
import org.ucl.fhirwork.common.framework.Executor;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.common.http.RestException;
import org.ucl.fhirwork.mapping.data.PatientFactory;
import org.ucl.fhirwork.mapping.data.PersonFactory;
import org.ucl.fhirwork.network.NetworkService;
import org.ucl.fhirwork.network.empi.data.Person;
import org.ucl.fhirwork.network.empi.server.EmpiServer;
import org.ucl.fhirwork.network.fhir.data.SearchParameter;
import org.ucl.fhirwork.network.fhir.operations.patient.UpdatePatientOperation;

import javax.inject.Inject;
import java.util.List;
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
    private Map<SearchParameter, String> parameters;
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
        UpdatePatientOperation updatePatient = (UpdatePatientOperation)operation;
        patient = updatePatient.getPatient();
        parameters = updatePatient.getSearchParameters();
    }

    @Override
    public Object invoke() throws ExecutionException
    {
        Person newPerson = personFactory.fromPatient(patient);
        Person oldPerson = findPerson(parameters);
        Person mergedPerson = merge(oldPerson, newPerson);
        Person updatedPerson = update(mergedPerson);
        return patientFactory.fromPerson(updatedPerson);
    }

    //TODO: Exception handling needs improvement
    private Person findPerson(Map<SearchParameter, String> searchParameters) throws ExecutionException
    {
        try {
            Person template = personFactory.fromSearchParameters(searchParameters);
            List<Person> people = empiServer.findPersonsByAttributes(template);

            if (people.isEmpty()){
                throw new ExecutionException("Missing resource");
            }
            if (people.size() > 1){
                throw new ExecutionException("Ambiguous resource");
            }
            return people.get(0);
        }
        catch (RestException cause){
            throw new ExecutionException(cause);
        }
    }

    //TODO: Merging needs improvement
    private Person merge(Person oldPerson, Person newPerson)
    {
        newPerson.setPersonId(oldPerson.getPersonId());
        return newPerson;
    }

    private Person update(Person newPerson) throws ExecutionException
    {
        try
        {
            return empiServer.updatePerson(newPerson);
        }
        catch (RestException cause){
            throw new ExecutionException(cause);
        }
    }
}
