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

import static org.mockito.Mockito.*;

import ca.uhn.fhir.model.primitive.IdDt;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ucl.fhirwork.common.framework.ExecutionException;
import org.ucl.fhirwork.common.network.Rest.RestException;
import org.ucl.fhirwork.mapping.data.PatientFactory;
import org.ucl.fhirwork.network.NetworkService;
import org.ucl.fhirwork.network.empi.server.EmpiServer;
import org.ucl.fhirwork.network.fhir.operations.patient.ReadPatientOperation;

public class ReadPatientExecutorTest
{
    private EmpiServer empiServer;
    private NetworkService networkService;
    private PatientFactory patientFactory;
    private ReadPatientExecutor executor;

    @Before
    public void setup()
    {
        empiServer = mock(EmpiServer.class);
        networkService = mock(NetworkService.class);
        when(networkService.getEmpiServer()).thenReturn(empiServer);

        patientFactory = Mockito.mock(PatientFactory.class);
        executor = new ReadPatientExecutor(networkService, patientFactory);
    }

    @Test
    public void setOperationTest()
    {
        ReadPatientOperation operation = new ReadPatientOperation(new IdDt(123));
        executor.setOperation(operation);
    }

    @Test (expected = NullPointerException.class)
    public void setOperationNullTest()
    {
        executor.setOperation(null);
    }

    @Test
    public void invokeTest() throws Exception
    {
        ReadPatientOperation operation = new ReadPatientOperation(new IdDt(123));
        executor.setOperation(operation);
        executor.invoke();

        //verify(empiServer.loadPerson(anyString()), times(1));
    }

    // Todo
//    @Test (expected = IllegalArgumentException.class)
//    public void invokeWithoutOperationTest() throws Exception
//    {
//        mapping.invoke();
//    }

    @Test (expected = ExecutionException.class)
    public void invokeConnectionErrorTest() throws Exception
    {
        when(empiServer.loadPerson(anyString())).thenThrow(RestException.class);
        ReadPatientOperation operation = new ReadPatientOperation(new IdDt(123));
        executor.setOperation(operation);
        executor.invoke();
    }
}
