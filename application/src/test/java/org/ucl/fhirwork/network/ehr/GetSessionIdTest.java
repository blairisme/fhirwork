package org.ucl.fhirwork.network.ehr;

import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.common.http.RestException;
import org.ucl.fhirwork.network.ehr.server.EhrServer;

public class GetSessionIdTest {

    @Test
    public void getSessionCodeTest() throws RestException{
        // Unit tests should not use an actual server, unit tests test a class in isolation.
        /*
        EhrServer ehrServer = new EhrServer();
        ehrServer.setUsername("oprn_jarrod");
        ehrServer.setPassword("ZayFYCiO644");
        ehrServer.setAddress("https://test.operon.systems/rest/v1");
        Assert.assertNotNull(ehrServer.getSessionId());
        ehrServer.deleteSessionId();
        */
    }

}
