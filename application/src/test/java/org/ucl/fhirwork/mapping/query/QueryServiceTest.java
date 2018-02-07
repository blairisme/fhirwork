/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping.query;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.ucl.fhirwork.common.serialization.JsonSerializer;
import org.ucl.fhirwork.configuration.Configuration;
import org.ucl.fhirwork.configuration.ConfigurationService;
import org.ucl.fhirwork.configuration.MappingConfiguration;
import org.ucl.fhirwork.test.TestResourceUtils;

import java.io.IOException;

public class QueryServiceTest
{
    @Test
    public void getQueryTest() throws IOException
    {
        ConfigurationService configurationService = getMockConfiguration();
        QueryService queryService = new QueryService(configurationService);

        String query = queryService.getQuery("3141-9", "c831fe4d-0ce9-4a63-8bfa-2c51007f97e5");
        Assert.assertNotNull(query);
        Assert.assertEquals(query,
            "select " +
                "body_weight/data[at0002]/origin/value as date, " +
                "body_weight/data[at0002]/events[at0003]/data[at0001]/items[at0004]/value/magnitude as magnitude, " +
                "body_weight/data[at0002]/events[at0003]/data[at0001]/items[at0004]/value/units as unit " +
            "from EHR [ehr_id/value='c831fe4d-0ce9-4a63-8bfa-2c51007f97e5'] " +
            "contains COMPOSITION c " +
            "contains OBSERVATION body_weight[openEHR-EHR-OBSERVATION.body_weight.v1]");
    }

    private ConfigurationService getMockConfiguration() throws IOException
    {
        JsonSerializer serializer = new JsonSerializer();
        String bundle = TestResourceUtils.readResource("configuration/mapping.json");
        MappingConfiguration mappingConfiguration = serializer.deserialize(bundle, MappingConfiguration.class);

        ConfigurationService configurationService = Mockito.mock(ConfigurationService.class);
        Mockito.when(configurationService.getConfiguration(Configuration.Mapping)).thenReturn(mappingConfiguration);
        return configurationService;
    }
}
