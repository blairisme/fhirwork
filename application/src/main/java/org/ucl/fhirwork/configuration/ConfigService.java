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

import org.ucl.fhirwork.common.runtime.JvmSingleton;
import org.ucl.fhirwork.common.serialization.JsonSerializer;
import org.ucl.fhirwork.common.serialization.SerializationException;
import org.ucl.fhirwork.common.serialization.Serializer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Instances of this class store the configuration used by the system. Methods
 * are provided to access and modify configuration, as well as to use
 * predefined configuration sets, relevant to different situations. I.E.,
 * testing or production.
 * 
 * @author Chenghui Fan
 * @author Blair Butterworth
 */
//TODO: Make thread safe
@Singleton
public class ConfigService implements Observer
{
    private Serializer serializer;
    private ConfigFileManager fileManager;
    private MappingConfig mappingConfig;
    private NetworkConfig networkConfig;
    private List<ConfigObserver> observers;
    private JvmSingleton updateFlag;

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
        this.observers = new ArrayList<>();
        this.updateFlag = new JvmSingleton(getClass().getName());
        this.updateFlag.addObserver(this);
    }

    public void addObserver(ConfigObserver observer) {
        observers.add(observer);
    }
    
    //added by Chenghui Fan
    public Collection<String> getAllLoinc(){
    	initializeConfig();
    	return mappingConfig.getAllLoinc();
    }

    public MappingConfigData getMappingConfig(String loinc) {
        initializeConfig();
        return mappingConfig.get(loinc);
    }

    public NetworkConfigData getNetworkConfig(NetworkConfigType type) {
        initializeConfig();
        return networkConfig.get(type);
    }

    public void setMappingConfig(String loinc, MappingConfigData config) {
        initializeConfig();
        mappingConfig = mappingConfig.set(loinc, config);
        updateMappingConfig();
        signalUpdate();
    }

    public void setNetworkConfig(NetworkConfigType type, NetworkConfigData config) {
        initializeConfig();
        networkConfig = networkConfig.set(type, config);
        updateNetworkConfig();
        signalUpdate();
    }

    private void initializeConfig(){
        if (mappingConfig == null || networkConfig == null) {
            initializeMappingConfig();
            initializeNetworkConfig();
        }
    }

	private void initializeMappingConfig() {
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

	private void initializeNetworkConfig() {
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

    private void updateMappingConfig() {
        try (Writer writer = fileManager.getConfigWriter(ConfigType.Mapping)){
            serializer.serialize(mappingConfig, MappingConfig.class, writer);
        }
        catch (IOException ioError){
            throw new ConfigIoException(ioError);
        }
        catch (SerializationException serializationError){
            throw new ConfigInvalidException(serializationError);
        }
    }

    private void updateNetworkConfig() {
        try (Writer writer = fileManager.getConfigWriter(ConfigType.Network)){
            serializer.serialize(networkConfig, NetworkConfig.class, writer);
        }
        catch (IOException ioError){
            throw new ConfigIoException(ioError);
        }
        catch (SerializationException serializationError){
            throw new ConfigInvalidException(serializationError);
        }
    }

    private void signalUpdate() {
        updateFlag.notifyObservers();
    }

    @Override
    public void update(Observable o, Object arg) {
        initializeMappingConfig();
        initializeNetworkConfig();
        notifyObservers();
    }

    private void notifyObservers() {
        for (ConfigObserver observer: observers){
            observer.configurationUpdated();
        }
    }
}
