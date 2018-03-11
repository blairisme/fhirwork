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

import java.io.*;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.ucl.fhirwork.common.paths.FilePaths;
import org.ucl.fhirwork.common.resources.ResourceUtils;
import org.ucl.fhirwork.common.serialization.JsonSerializer;
import org.ucl.fhirwork.common.serialization.Serializer;
import org.ucl.fhirwork.configuration.exception.ConfigIoException;
import org.ucl.fhirwork.configuration.data.ConfigType;

import javax.inject.Inject;

/**
 * This class is responsible for providing readers and writers of the configuration files
 *
 * @author Chenghui Fan
 * @author Blair Butterworth
 */
public class ConfigFileManager {

	private static final String DEFAULT_MANIFEST_NAME = "configFilePath.json";
    private static final String DEFAULT_CONFIG_DIR = ".fhirwork";

	private File manifest;
	private File directory;
    private Environment environment;
    private ConfigFileList configFileList;

	@Inject
	public ConfigFileManager() {
        setEnvironment(Environment.Production);
        setConfigManifest(ResourceUtils.getResource(DEFAULT_MANIFEST_NAME));
        setConfigDirectory(FilePaths.getUserDir(DEFAULT_CONFIG_DIR));
    }

    public Reader getConfigReader(ConfigType type) throws ConfigIoException {
	    try {
            String configPath = getConfig().get(type);
            File configFile = getResource(configPath);
            return new FileReader(configFile);
        }
        catch (IOException error){
	        throw new ConfigIoException(error);
        }
    }

    public Writer getConfigWriter(ConfigType type) throws ConfigIoException {
        try {
            String configPath = getConfig().get(type);
            File configFile = getResource(configPath);
            return new FileWriter(configFile);
        }
        catch (IOException error){
            throw new ConfigIoException(error);
        }
    }

    public void setConfigDirectory(File directory) {
        this.directory = directory;
    }

    public void setConfigManifest(File manifest) {
        this.manifest = manifest;
        resetConfig();
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
        resetConfig();
    }

    private Map<ConfigType, String> getConfig() throws ConfigIoException {
        initializeConfig();
        return configFileList.get(environment);
    }

    private void resetConfig() {
        configFileList = null;
    }

    private void initializeConfig() throws ConfigIoException {
        if (configFileList == null){
            configFileList = loadConfig();
        }
    }

    private ConfigFileList loadConfig() throws ConfigIoException {
        try (Reader listReader = new FileReader(manifest)){
            Serializer serializer = new JsonSerializer();
            return serializer.deserialize(listReader, ConfigFileList.class);
        }
        catch (Exception error) {
            throw new ConfigIoException(error);
        }
	}

    private File getResource(String resource) throws IOException {
	    File resourcePath = new File(directory, resource);
	    if (! resourcePath.exists()) {
            File defaultPath = ResourceUtils.getResource(resource);
            FileUtils.copyFile(defaultPath, resourcePath);
        }
	    return resourcePath;
    }
}
