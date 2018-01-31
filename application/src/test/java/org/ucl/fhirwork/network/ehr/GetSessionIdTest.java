package org.ucl.fhirwork.network.ehr;

import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.common.http.RestException;
import org.ucl.fhirwork.network.ehr.server.EhrServer;

public class GetSessionIdTest {

    @Test
    public void getSessionCodeTest() throws RestException{
        EhrServer ehrServer = new EhrServer();
        ehrServer.setUsername("oprn_jarrod");
        ehrServer.setPassword("ZayFYCiO644");
//        System.out.println("https://test.operon.systems");
        ehrServer.setAddress("https://test.operon.systems/rest/v1");
        Assert.assertNotNull(ehrServer.getSessionId());
        ehrServer.deleteSessionId();
    }

}
