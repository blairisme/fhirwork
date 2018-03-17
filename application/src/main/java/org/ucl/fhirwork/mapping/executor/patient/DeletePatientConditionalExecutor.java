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

import org.apache.commons.lang3.Validate;
import org.ucl.fhirwork.common.framework.ExecutionException;
import org.ucl.fhirwork.common.framework.Executor;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.mapping.data.PersonFactory;
import org.ucl.fhirwork.network.NetworkService;
import org.ucl.fhirwork.network.empi.data.InternalIdentifier;
import org.ucl.fhirwork.network.empi.data.Person;
import org.ucl.fhirwork.network.empi.server.EmpiServer;
import org.ucl.fhirwork.network.fhir.data.SearchParameter;
import org.ucl.fhirwork.network.fhir.operations.patient.DeletePatientOperation;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;

/**
 * Instances of this class convert the conditional delete patient FHIR
 * operation into the appropriate EMPI service calls.
 *
 * @author Blair Butterworth
 */
public class DeletePatientConditionalExecutor implements Executor
{
    private Map<SearchParameter, Object> searchParameters;
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
        Validate.notNull(operation);
        DeletePatientOperation deletePatient = (DeletePatientOperation)operation;
        searchParameters = deletePatient.getSearchParameters();
    }

    @Override
    public Object invoke() throws ExecutionException
    {
        try
        {
            Person template = personFactory.fromSearchParameters(searchParameters);
            Collection<Person> people = empiServer.findPersons(template);

            for (Person person: people){
                empiServer.removePerson(person.getInternalIdentifier());
            }
            return null;
        }
        catch (Throwable cause){
            throw new ExecutionException(cause);
        }
    }
}