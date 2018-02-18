/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.common.resources.Resources;

import java.io.File;

public class ConfigServiceTest
{
    @Test
    public void getNetworkConfigTest()
    {
        ConfigFileManager configFileManager = new ConfigFileManager();
        configFileManager.setConfigListPath(Resources.getResource("configuration/manifest.json"));

        ConfigService configService = new ConfigService(configFileManager);
        NetworkConfigData networkConfig = configService.getNetworkConfig(NetworkConfigType.Empi);

        Assert.assertEquals("http://localhost:8080", networkConfig.getAddress());
    }

    @Test
    public void getMappingConfigTest()
    {
        ConfigFileManager configFileManager = new ConfigFileManager();
        configFileManager.setConfigListPath(Resources.getResource("configuration/manifest.json"));

        ConfigService configService = new ConfigService(configFileManager);
        MappingConfigData mappingConfig = configService.getMappingConfig("3141-9");

        Assert.assertEquals("openEHR-EHR-OBSERVATION.body_weight.v1", mappingConfig.getArchetype());
    }

    @Test (expected = ConfigMissingException.class)
    public void getMissingMappingConfigTest()
    {
        ConfigFileManager configFileManager = new ConfigFileManager();
        configFileManager.setConfigListPath(Resources.getResource("configuration/manifest.json"));

        ConfigService configService = new ConfigService(configFileManager);
        configService.getMappingConfig("123");
    }

    @Test (expected = ConfigIoException.class)
    public void missingConfigTest()
    {
        ConfigFileManager configFileManager = new ConfigFileManager();
        configFileManager.setConfigListPath(new File("/doesnt/exist/foo.json"));

        ConfigService configService = new ConfigService(configFileManager);
        configService.getMappingConfig("3141-9");
    }

    @Test
    public void setNetworkConfigTest()
    {
        ConfigFileManager configFileManager = new ConfigFileManager();
        configFileManager.setConfigListPath(Resources.getResource("configuration/manifest_overwrite.json"));

        ConfigService configService = new ConfigService(configFileManager);
        NetworkConfigData expected = new NetworkConfigData("http://different.com", "not", "thesame");

        configService.setNetworkConfig(NetworkConfigType.Empi, expected);
        NetworkConfigData actual = configService.getNetworkConfig(NetworkConfigType.Empi);

        Assert.assertEquals(expected.getAddress(), actual.getAddress());
    }
}
