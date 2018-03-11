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

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ucl.fhirwork.common.paths.FilePaths;
import org.ucl.fhirwork.common.resources.ResourceUtils;
import org.ucl.fhirwork.configuration.data.*;
import org.ucl.fhirwork.configuration.exception.ConfigIoException;
import org.ucl.fhirwork.configuration.persistence.ConfigFileManager;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ConfigServiceTest
{
    private ConfigFileManager configFileManager;

    @Before
    public void setup() {
        configFileManager = new ConfigFileManager();
        configFileManager.setConfigDirectory(FilePaths.getTempDir("fhirwork"));
    }

    @After
    public void tearDown() throws IOException {
        FileUtils.deleteDirectory(FilePaths.getTempDir("fhirwork"));
    }

    @Test
    public void getNetworkConfigTest()
    {
        configFileManager.setConfigManifest(ResourceUtils.getResource("configuration/manifest.json"));

        ConfigService configService = new ConfigService(configFileManager);
        NetworkConfig networkConfig = configService.getConfig(ConfigType.Network);
        NetworkConfigData networkData = networkConfig.getData(NetworkConfigType.Empi);

        Assert.assertEquals("http://localhost:8080", networkData.getAddress());
    }

    @Test
    public void getMappingConfigTest()
    {
        configFileManager.setConfigManifest(ResourceUtils.getResource("configuration/manifest.json"));

        ConfigService configService = new ConfigService(configFileManager);
        MappingConfig mappingConfig = configService.getConfig(ConfigType.Mapping);
        Map<String, BasicMappingConfig> simpleConfig = mappingConfig.getBasic();
        BasicMappingConfig mappingData = simpleConfig.get("3141-9");

        Assert.assertEquals("openEHR-EHR-OBSERVATION.body_weight.v1", mappingData.getArchetype());
    }

    @Test (expected = ConfigIoException.class)
    public void missingConfigTest()
    {
        configFileManager.setConfigManifest(new File("/doesnt/exist/foo.json"));

        ConfigService configService = new ConfigService(configFileManager);
        configService.getConfig(ConfigType.Mapping);
    }

    @Test
    public void setNetworkConfigTest()
    {
        configFileManager.setConfigManifest(ResourceUtils.getResource("configuration/manifest_overwrite.json"));

        ConfigService configService = new ConfigService(configFileManager);
        NetworkConfig expected = new NetworkConfig(
            new NetworkConfigData("http://ehr.com", "ehr", "123"),
            new NetworkConfigData("http://empi.com", "empi", "456"));

        configService.setConfig(ConfigType.Network, expected);
        NetworkConfig actual = configService.getConfig(ConfigType.Network);

        Assert.assertEquals(expected, actual);
    }
}
