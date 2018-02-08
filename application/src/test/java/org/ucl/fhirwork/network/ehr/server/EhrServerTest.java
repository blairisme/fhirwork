/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.network.ehr.server;

import com.google.common.collect.ImmutableMap;
import static com.google.common.collect.ImmutableBiMap.of;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.ucl.fhirwork.common.http.*;
import org.ucl.fhirwork.network.ehr.data.*;
import org.ucl.fhirwork.test.MockProvider;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
public class EhrServerTest {
    private RestRequest request;
    private RestResponse response;
    private RestServer restServer;
    private EhrServer ehrServer;
    private SessionToken sessionToken;

    @Before
    public  void setUp() throws RestException{
        request  = mock(RestRequest.class);
        response  = mock(RestResponse.class);
        restServer = mock(RestServer.class);
        ehrServer = new EhrServer(new MockProvider(restServer));
        ehrServer.setUsername("testUserName");
        ehrServer.setPassword("testPassword");
        ehrServer.setAddress("testAddress");
        sessionToken = mock(SessionToken.class);

        when(restServer.post(any(RestResource.class))).thenReturn(request);
        when(restServer.get(any(EhrResource.class))).thenReturn(request);
        when(request.setParameters(any(ImmutableMap.class))).thenReturn(request);
        when(request.make(any(HandleFailure.class))).thenReturn(response);
        when(response.asType((SessionToken.class))).thenReturn(sessionToken);
        when(sessionToken.getSessionId()).thenReturn("session");


    }

    @Test
    public void queryTest() throws RestException {
        String query = "";

        when(response.getStatusCode()).thenReturn(200);

        QueryBundle queryBundle = mock(QueryBundle.class);
        when(response.asType(QueryBundle.class)).thenReturn(queryBundle);

        QueryBundle queryResult = ehrServer.query(query);

        verify(restServer, times(1)).get(EhrResource.Query);
        verify(request, times(1)).setParameters(of(EhrParameter.Aql,query));
        verify(response, times(1)).asType(QueryBundle.class);
        Assert.assertEquals(queryResult, queryBundle);

    }

    @Test
    public void getEhrTest() throws RestException{

        String id = "testId";
        String namespace = "testNamespace";

        when(response.getStatusCode()).thenReturn(200);

        HealthRecord expectedResult = mock(HealthRecord.class);
        when(response.asType(HealthRecord.class)).thenReturn(expectedResult);
        HealthRecord result = ehrServer.getEhr(id, namespace);

        verify(restServer, times(1)).get(EhrResource.Ehr);
        verify(request, times(1)).setParameters(of(EhrParameter.SubjectId, id, EhrParameter.SubjectNamespace, namespace));
        verify(response, times(1)).asType(HealthRecord.class);
        Assert.assertEquals(expectedResult, result);

    }

    @Test
    public void getCompositionsTest() throws RestException, InstantiationException, IllegalAccessException{

        Composition composition = mock(Composition.class);
        CompositionResult compositionResult = mock(CompositionResult.class);
        CompositionBundle bundle = mock(CompositionBundle.class);
        List<CompositionResult> compositionList = new ArrayList<>();
        compositionList.add(compositionResult);

        when(ehrServer.query("", CompositionBundle.class)).thenReturn(bundle);
        when(bundle.getResultSet()).thenReturn(compositionList);
        when(compositionResult.getComposition()).thenReturn(composition);

        List<Composition> result = ehrServer.getCompositions("");

        for (Composition com : result){
            Assert.assertEquals(composition,com);
        }




    }
}
