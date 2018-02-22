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
import org.ucl.fhirwork.configuration.ConfigService;
import org.ucl.fhirwork.configuration.data.ConfigType;
import org.ucl.fhirwork.configuration.data.MappingConfig;
import org.ucl.fhirwork.configuration.data.MappingConfigData;

public class QueryServiceTest
{
    @Test
    public void getQueryTest()
    {
        ConfigService configuration = getMockConfiguration();
        QueryService queryService = new QueryService(configuration);

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

    private ConfigService getMockConfiguration()
    {
        MappingConfigData configData = new MappingConfigData(
            "body_weight",
            "openEHR-EHR-OBSERVATION.body_weight.v1",
            "data[at0002]/origin/value",
            "data[at0002]/events[at0003]/data[at0001]/items[at0004]/value/magnitude",
            "data[at0002]/events[at0003]/data[at0001]/items[at0004]/value/units");

        ConfigService configService = Mockito.mock(ConfigService.class);
        MappingConfig mappingConfig = Mockito.mock(MappingConfig.class);

        Mockito.when(mappingConfig.getData("3141-9")).thenReturn(configData);
        Mockito.when(configService.getConfig(ConfigType.Mapping)).thenReturn(mappingConfig);

        return configService;
    }
}
