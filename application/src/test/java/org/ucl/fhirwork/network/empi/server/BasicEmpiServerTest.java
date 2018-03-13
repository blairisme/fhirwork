/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.empi.server;

import org.junit.Before;
import org.junit.Test;
import org.ucl.fhirwork.common.network.Rest.*;
import org.ucl.fhirwork.common.network.exception.ResourceExistsException;
import org.ucl.fhirwork.network.empi.data.Person;
import org.ucl.fhirwork.test.MockProvider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

public class BasicEmpiServerTest
{
    private RestRequest request;
    private RestResponse response;
    private RestServer restServer;
    private EmpiServer empiServer;

    @Before
    @SuppressWarnings("unchecked")
    public void setup() throws Exception
    {
        request  = mock(RestRequest.class);
        response  = mock(RestResponse.class);
        restServer = mock(AuthenticatedRestServer.class);
        empiServer = new BasicEmpiServer(new MockProvider(restServer));

        when(restServer.put(any(RestResource.class))).thenReturn(request);
        when(request.make()).thenReturn(response);
        when(response.asString()).thenReturn("session");
    }

    @Test
    public void addPersonTest() throws RestException
    {
        Person person = new Person();
        Person result = empiServer.addPerson(person);

        assertNotEquals(person, result);
        verify(restServer, times(1)).put(EmpiResource.AddPerson);
        verify(request, times(1)).setBody(person, Person.class);
        verify(response, times(1)).asType(Person.class);
    }

    @Test (expected = ResourceExistsException.class)
    public void addPersonAlreadyExistsTest() throws RestException
    {
        when(response.getStatusCode()).thenReturn(204);

        Person person = new Person();
        Person result = empiServer.addPerson(person);

        assertEquals(person, result);
        verify(restServer, times(1)).put(EmpiResource.AddPerson);
        verify(request, times(1)).setBody(person, Person.class);
        verify(response, times(0)).asType(Person.class);
    }

    @Test (expected = RestException.class)
    public void addPersonConnectionExceptionTest() throws RestException
    {
        when(request.make()).thenThrow(RestException.class);
        empiServer.addPerson(new Person());
    }
}
