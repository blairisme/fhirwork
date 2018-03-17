/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.configuration.persistence;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ucl.fhirwork.common.paths.FilePaths;
import org.ucl.fhirwork.common.resources.ResourceUtils;
import org.ucl.fhirwork.common.serialization.JsonSerializer;
import org.ucl.fhirwork.common.serialization.Serializer;
import org.ucl.fhirwork.configuration.data.ConfigType;
import org.ucl.fhirwork.configuration.data.NetworkConfig;
import org.ucl.fhirwork.configuration.data.NetworkConfigData;
import org.ucl.fhirwork.configuration.exception.ConfigIoException;

import java.io.*;

public class ConfigFileManagerTest
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
	public void loadConfigurationTest() throws Exception
    {
        try (Reader reader = configFileManager.getConfigReader(ConfigType.Mapping)){
            Assert.assertNotNull(reader);
        }
    }

    @Test(expected = ConfigIoException.class)
    public void missingConfigurationTest()
    {
        configFileManager.setConfigManifest(new File("/doesnt/exist/foo.json"));
        configFileManager.getConfigReader(ConfigType.Mapping);
    }

    @Test
    public void customConfigurationTest() throws Exception
    {
        configFileManager.setConfigManifest(ResourceUtils.getResource("configuration/manifest.json"));
        try (Reader reader = configFileManager.getConfigReader(ConfigType.Mapping)){
            Assert.assertNotNull(reader);
        }
    }

    @Test
    public void getConfigReaderTest() throws Exception
    {
        try (Reader reader = configFileManager.getConfigReader(ConfigType.Mapping)) {
            StringWriter writer = new StringWriter();
            IOUtils.copy(reader, writer);

            String content = writer.toString();
            Assert.assertTrue(! content.isEmpty());
        }
    }

    @Test
    public void getConfigWriterTest() throws Exception
    {
        configFileManager.setConfigManifest(ResourceUtils.getResource("configuration/manifest_overwrite.json"));

        try (Writer writer = configFileManager.getConfigWriter(ConfigType.Network)) {

            NetworkConfigData ehrConfig = new NetworkConfigData("http://ehr.com", "user", "pass");
            NetworkConfigData empiConfig = new NetworkConfigData("http://empi.com", "user", "pass");
            NetworkConfig networkConfig = new NetworkConfig(empiConfig, ehrConfig);

            Serializer serializer = new JsonSerializer();
            serializer.serialize(networkConfig, NetworkConfig.class, writer);
        }

        try (Reader reader = configFileManager.getConfigReader(ConfigType.Network)) {

            Serializer serializer = new JsonSerializer();
            NetworkConfig networkConfig = serializer.deserialize(reader, NetworkConfig.class);

            Assert.assertEquals("http://ehr.com", networkConfig.getEhr().getAddress());
            Assert.assertEquals("http://empi.com", networkConfig.getEmpi().getAddress());
        }
    }
}
