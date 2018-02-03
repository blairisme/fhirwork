package org.ucl.fhirwork.network.ehr;

import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.common.http.RestException;
import org.ucl.fhirwork.network.ehr.data.Composition;
import org.ucl.fhirwork.network.ehr.server.EhrServer;

import java.util.ArrayList;
import java.util.List;

public class GetCompositionTest
{
    @Test
    public void  getComositionTest() throws RestException {
        // Unit tests should not use an actual server, unit tests test a class in isolation.
        /*
        EhrServer ehrServer = new EhrServer();
        ehrServer.setUsername("oprn_jarrod");
        ehrServer.setPassword("ZayFYCiO644");
        ehrServer.setAddress("https://test.operon.systems/rest/v1");
        List<Composition> compositions = ehrServer.getCompositions("cf18d531-4ff6-4388-a618-3ddd96d5e45e");
        Assert.assertFalse(compositions.isEmpty());
        for(Composition result : compositions){
            System.out.println(result.getUid().getValue());
        }
        */
    }
}
