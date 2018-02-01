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

import org.ucl.fhirwork.common.framework.ExecutionException;
import org.ucl.fhirwork.common.framework.Executor;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.common.http.RestException;
import org.ucl.fhirwork.mapping.data.PersonFactory;
import org.ucl.fhirwork.network.NetworkService;
import org.ucl.fhirwork.network.empi.data.Person;
import org.ucl.fhirwork.network.empi.server.EmpiServer;
import org.ucl.fhirwork.network.fhir.data.SearchParameter;
import org.ucl.fhirwork.network.fhir.operations.patient.DeletePatientOperation;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class DeletePatientConditionalExecutor implements Executor
{
    private String personId;
    private Map<SearchParameter, String> searchParameters;
    private EmpiServer empiServer;
    private PersonFactory personFactory;

    @Inject
    public DeletePatientConditionalExecutor(
            NetworkService networkService,
            PersonFactory personFactory)
    {
        this.empiServer = networkService.getEmpiServer();
        this.personFactory = personFactory;
    }

    @Override
    public void setOperation(Operation operation)
    {
        DeletePatientOperation deletePatient = (DeletePatientOperation)operation;
        personId = deletePatient.getPatientId().getIdPart();
        searchParameters = deletePatient.getSearchParameters();
    }

    @Override
    public Object invoke() throws ExecutionException
    {
        try
        {
            Person template = personFactory.fromSearchParameters(searchParameters);
            List<Person> people = empiServer.findPersonsByAttributes(template);

            for (Person person: people){
                empiServer.removePerson(person.getPersonId());
            }
            return null;
        }
        catch (RestException cause){
            throw new ExecutionException(cause);
        }
    }
}