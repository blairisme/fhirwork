/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.test;

import org.ucl.fhirwork.configuration.ConfigService;
import org.ucl.fhirwork.configuration.data.ConfigType;
import org.ucl.fhirwork.configuration.data.GeneralConfig;
import org.ucl.fhirwork.configuration.data.MappingConfig;
import org.ucl.fhirwork.configuration.data.NetworkConfig;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockConfigService
{
    public static ConfigService get()
    {
        GeneralConfig generalConfig = mock(GeneralConfig.class);
        NetworkConfig networkConfig = mock(NetworkConfig.class);
        MappingConfig mappingConfig = mock(MappingConfig.class);
        ConfigService configService = mock(ConfigService.class);

        when(configService.getConfig(ConfigType.General)).thenReturn(generalConfig);
        when(configService.getConfig(ConfigType.Network)).thenReturn(networkConfig);
        when(configService.getConfig(ConfigType.Mapping)).thenReturn(mappingConfig);

        return configService;
    }
}
