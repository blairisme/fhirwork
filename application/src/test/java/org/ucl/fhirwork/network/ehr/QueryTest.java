package org.ucl.fhirwork.network.ehr;

import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.common.http.RestException;
import org.ucl.fhirwork.network.ehr.data.QueryBundle;
import org.ucl.fhirwork.network.ehr.server.EhrServer;

public class QueryTest {
    @Test
    public void queryTest() throws RestException {

        EhrServer ehrServer = new EhrServer();
        ehrServer.setUsername("oprn_jarrod");
        ehrServer.setPassword("ZayFYCiO644");
        ehrServer.setAddress("https://test.operon.systems/rest/v1");
        ehrServer.getSessionId();
        QueryBundle result = ehrServer.query("select a from EHR [ehr_id/value='" + "9999999332" + "'] contains COMPOSITION a");
        Assert.assertNotNull(result);
    }
}
