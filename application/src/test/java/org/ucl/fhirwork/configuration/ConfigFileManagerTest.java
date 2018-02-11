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

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.common.resources.Resources;
import org.ucl.fhirwork.common.serialization.JsonSerializer;
import org.ucl.fhirwork.common.serialization.Serializer;
import org.ucl.fhirwork.test.TestResourceUtils;

import java.io.File;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class ConfigFileManagerTest
{
	@Test
	public void loadConfigurationTest() throws Exception
    {
        ConfigFileManager configFileManager = new ConfigFileManager();
        try (Reader reader = configFileManager.getConfigReader(ConfigType.Mapping)){
            Assert.assertNotNull(reader);
        }
    }

    @Test(expected = ConfigIoException.class)
    public void missingConfigurationTest()
    {
        ConfigFileManager configFileManager = new ConfigFileManager();
        configFileManager.setConfigListPath(new File("/doesnt/exist/foo.json"));
        configFileManager.getConfigReader(ConfigType.Mapping);
    }

    @Test
    public void customConfigurationTest() throws Exception
    {
        ConfigFileManager configFileManager = new ConfigFileManager();
        configFileManager.setConfigListPath(Resources.getResource("configuration/manifest.json"));
        try (Reader reader = configFileManager.getConfigReader(ConfigType.Mapping)){
            Assert.assertNotNull(reader);
        }
    }

    @Test
    public void getConfigReaderTest() throws Exception
    {
        ConfigFileManager configFileManager = new ConfigFileManager();
        try (Reader reader = configFileManager.getConfigReader(ConfigType.Mapping)){
            StringWriter writer = new StringWriter();
            IOUtils.copy(reader, writer);

            String content = writer.toString();
            Assert.assertTrue(! content.isEmpty());
        }
    }

    @Test
    public void getConfigWriterTest() throws Exception
    {
        ConfigFileManager configFileManager = new ConfigFileManager();
        configFileManager.setConfigListPath(Resources.getResource("configuration/manifest_overwrite.json"));

        try (Writer writer = configFileManager.getConfigWriter(ConfigType.Network)){

            NetworkConfigData ehrConfig = new NetworkConfigData("http://ehr.com", "user", "pass");
            NetworkConfigData empiConfig = new NetworkConfigData("http://empi.com", "user", "pass");
            NetworkConfig networkConfig = new NetworkConfig(empiConfig, ehrConfig);

            Serializer serializer = new JsonSerializer();
            serializer.serialize(networkConfig, NetworkConfig.class, writer);
        }

        try (Reader reader = configFileManager.getConfigReader(ConfigType.Network)){

            Serializer serializer = new JsonSerializer();
            NetworkConfig networkConfig = serializer.deserialize(reader, NetworkConfig.class);

            Assert.assertEquals("http://ehr.com", networkConfig.getEhr().getAddress());
            Assert.assertEquals("http://empi.com", networkConfig.getEmpi().getAddress());
        }
    }
}
