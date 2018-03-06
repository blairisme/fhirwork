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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ucl.fhirwork.common.network.Rest.*;
import org.ucl.fhirwork.network.ehr.data.HealthRecord;
import org.ucl.fhirwork.network.ehr.data.ObservationBundle;
import org.ucl.fhirwork.network.ehr.data.SessionToken;
import org.ucl.fhirwork.test.MockProvider;

import static com.google.common.collect.ImmutableBiMap.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class EhrServerTest {
    private RestRequest request;
    private RestResponse response;
    private RestServer restServer;
    private EhrServer ehrServer;
    private SessionToken sessionToken;

    @Before
    public void setUp() throws RestException {
        request  = mock(RestRequest.class);
        response  = mock(RestResponse.class);
        restServer = mock(AuthenticatedRestServer.class);
        ehrServer = new BasicEhrServer(new MockProvider(restServer));
        ehrServer.setConnectionDetails("testAddress", "testUserName", "testPassword");
        sessionToken = mock(SessionToken.class);

        when(restServer.post(any(RestResource.class))).thenReturn(request);
        when(restServer.get(any(EhrResource.class))).thenReturn(request);
        when(request.setParameters(any(ImmutableMap.class))).thenReturn(request);
        when(request.make(any(RestStatusHandler.class))).thenReturn(response);
        when(response.asType((SessionToken.class))).thenReturn(sessionToken);
        when(sessionToken.getSessionId()).thenReturn("session");
    }

    @Test
    public void queryTest() throws RestException
    {
        String query = "";

        when(response.getStatusCode()).thenReturn(200);

        ObservationBundle expected = mock(ObservationBundle.class);
        when(response.asType(ObservationBundle.class)).thenReturn(expected);

        ObservationBundle actual = ehrServer.query(query, ObservationBundle.class);

        verify(restServer, times(1)).get(EhrResource.Query);
        verify(request, times(1)).setParameters(of(EhrParameter.Aql,query));
        verify(response, times(1)).asType(ObservationBundle.class);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getEhrTest() throws RestException
    {
        String id = "testId";
        String namespace = "testNamespace";

        when(response.getStatusCode()).thenReturn(200);

        HealthRecord expectedResult = mock(HealthRecord.class);
        when(response.asType(HealthRecord.class)).thenReturn(expectedResult);
        HealthRecord result = ehrServer.getHealthRecord(id, namespace);

        verify(restServer, times(1)).get(EhrResource.Ehr);
        verify(request, times(1)).setParameters(of(EhrParameter.SubjectId, id, EhrParameter.SubjectNamespace, namespace));
        verify(response, times(1)).asType(HealthRecord.class);
        Assert.assertEquals(expectedResult, result);
    }
}
