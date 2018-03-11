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

import org.ucl.fhirwork.common.resources.Resources;
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
//TODO: Make thread safe
public class ConfigFileManager {

	public static final String DEFAULT_PATH_FILE_LOCATION = "configFilePath.json";

	private File listLocation;
    private Environment environment;
    private ConfigFileList configFileList;

	@Inject
	public ConfigFileManager()
    {
        setEnvironment(Environment.Production);
        setConfigListPath(Resources.getResource(DEFAULT_PATH_FILE_LOCATION));
    }

    public Reader getConfigReader(ConfigType type) throws ConfigIoException
    {
	    try {
            String configPath = getConfig().get(type);
            File configFile = Resources.getResource(configPath);
            return new FileReader(configFile);
        }
        catch (FileNotFoundException error){
	        throw new ConfigIoException(error);
        }
    }

    public Writer getConfigWriter(ConfigType type) throws ConfigIoException
    {
        try {
            String configPath = getConfig().get(type);
            File configFile = Resources.getResource(configPath);
            return new FileWriter(configFile);
        }
        catch (IOException error){
            throw new ConfigIoException(error);
        }
    }

    public void setConfigListPath(File listLocation) {
        this.listLocation = listLocation;
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

    private void resetConfig(){
        configFileList = null;
    }

    private void initializeConfig() throws ConfigIoException
    {
        if (configFileList == null){
            configFileList = loadConfig();
        }
    }

    private ConfigFileList loadConfig() throws ConfigIoException
    {
        try (Reader listReader = new FileReader(listLocation)){
            Serializer serializer = new JsonSerializer();
            return serializer.deserialize(listReader, ConfigFileList.class);
        }
        catch (Exception error) {
            throw new ConfigIoException(error);
        }
	}
}
