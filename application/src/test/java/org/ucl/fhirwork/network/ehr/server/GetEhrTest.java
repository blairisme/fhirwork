package org.ucl.fhirwork.network.ehr.server;

import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.common.http.RestException;
import org.ucl.fhirwork.network.ehr.data.HealthRecord;
import org.ucl.fhirwork.network.ehr.server.EhrServer;

public class GetEhrTest {

    @Test
    public void getEhrTest() throws RestException{
        // Unit tests should not use an actual server, unit tests test a class in isolation.
        /*
        EhrServer ehrServer = new EhrServer();
        ehrServer.setUsername("oprn_jarrod");
        ehrServer.setPassword("ZayFYCiO644");
        ehrServer.setAddress("https://test.operon.systems/rest/v1");
        HealthRecord record = ehrServer.getEhr("9999999026","uk.nhs.nhs_number");
        System.out.println(record.getEhrId());
        Assert.assertNotNull(record);
        */
    }
}
