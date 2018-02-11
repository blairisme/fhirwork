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

import org.ucl.fhirwork.common.serialization.JsonSerializer;
import org.ucl.fhirwork.common.serialization.SerializationException;
import org.ucl.fhirwork.common.serialization.Serializer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.Reader;

/**
 * Instances of this class store the configuration used by the system. Methods
 * are provided to access and modify configuration, as well as to use
 * predefined configuration sets, relevant to different situations. I.E.,
 * testing or production.
 * 
 * @author Chenghui Fan
 * @author Blair Butterworth
 */
@Singleton
public class ConfigService
{
    private Serializer serializer;
    private ConfigFileManager fileManager;
    private MappingConfig mappingConfig;
    private NetworkConfig networkConfig;

    /**
     * Constructs a new instance of this class given a {@link ConfigFileManager}
     * used to read and write configuration data.
     *
     * @param fileManager a {@code ConfigFileManager} instance.
     */
    @Inject
    public ConfigService(ConfigFileManager fileManager) {
        this.fileManager = fileManager;
        this.serializer = new JsonSerializer();
    }

    public MappingConfigData getMappingConfig(String loinc) {
        loadConfig();
        return mappingConfig.get(loinc);
    }

    public NetworkConfigData getNetworkConfig(NetworkConfigType type) {
        loadConfig();
        return networkConfig.get(type);
    }

    private void loadConfig(){
        if (mappingConfig == null || networkConfig == null){
            registerMappingConfig();
            registerNetworkConfig();
        }
    }

	private void registerMappingConfig() {
        try (Reader configFile = fileManager.getConfigReader(ConfigType.Mapping)) {
            mappingConfig = serializer.deserialize(configFile, MappingConfig.class);
        }
        catch (IOException ioError){
            throw new ConfigIoException(ioError);
        }
        catch (SerializationException serializationError){
            throw new ConfigInvalidException(serializationError);
        }
	}

	private void registerNetworkConfig() {
        try(Reader configFile = fileManager.getConfigReader(ConfigType.Network)){
            networkConfig = serializer.deserialize(configFile, NetworkConfig.class);
        }
        catch (IOException ioError){
            throw new ConfigIoException(ioError);
        }
        catch (SerializationException serializationError){
            throw new ConfigInvalidException(serializationError);
        }
	}
}
