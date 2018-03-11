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

import com.google.common.collect.ImmutableMap;
import org.ucl.fhirwork.common.runtime.JvmSingleton;
import org.ucl.fhirwork.common.serialization.JsonSerializer;
import org.ucl.fhirwork.common.serialization.SerializationException;
import org.ucl.fhirwork.common.serialization.Serializer;
import org.ucl.fhirwork.configuration.data.*;
import org.ucl.fhirwork.configuration.exception.ConfigInvalidException;
import org.ucl.fhirwork.configuration.exception.ConfigIoException;
import org.ucl.fhirwork.configuration.persistence.ConfigFileManager;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;

/**
 * Instances of this class store the configuration used by the system. Methods
 * are provided to access and modify configuration, as well as to use
 * predefined configuration sets, relevant to different situations. I.E.,
 * testing or production.
 * 
 * @author Chenghui Fan
 * @author Blair Butterworth
 */
//Todo: Consider replacing with https://github.com/lightbend/config
@Singleton
@SuppressWarnings("unchecked")
public class ConfigService
{
    private Serializer serializer;
    private JvmSingleton updateFlag;
    private ConfigFileManager fileManager;
    private List<ConfigObserver> observers;
    private Map<ConfigType, Object> configData;
    private Map<ConfigType, Class<?>> configRegistry;

    @Inject
    public ConfigService(ConfigFileManager fileManager) {
        this.fileManager = fileManager;
        this.serializer = new JsonSerializer();
        this.observers = new ArrayList<>();
        this.updateFlag = new JvmSingleton(getClass().getName(), new ConfigurationObserver());
        this.configRegistry = getSupportConfigTypes();
    }

    private Map<ConfigType, Class<?>> getSupportConfigTypes() {
        return ImmutableMap.of(
            ConfigType.Cache, CacheConfig.class,
            ConfigType.Mapping, MappingConfig.class,
            ConfigType.Network, NetworkConfig.class,
            ConfigType.General, GeneralConfig.class);
    }

    public void addObserver(ConfigObserver observer) {
        observers.add(observer);
    }

    public <T> T getConfig(ConfigType type) {
        readConfig();
        return (T)configData.get(type);
    }

    public <T> void setConfig(ConfigType type, T config) {
        readConfig();
        configData.put(type, config);
        writeConfig();
        signalUpdate();
    }

    private void readConfig() {
        if (configData == null) {
            configData = new HashMap<>();
            for (Map.Entry<ConfigType, Class<?>> entry: configRegistry.entrySet()) {
                configData.put(entry.getKey(), readConfig(entry.getKey(), entry.getValue()));
            }
        }
    }

    private void writeConfig() {
        if (configData != null) {
            for (Map.Entry<ConfigType, Class<?>> entry: configRegistry.entrySet()) {
                writeConfig(entry.getKey(), configData.get(entry.getKey()), entry.getValue());
            }
        }
    }

    private <T> T readConfig(ConfigType type, Class<T> clazz) {
        try (Reader configFile = fileManager.getConfigReader(type)) {
            return serializer.deserialize(configFile, clazz);
        }
        catch (IOException ioError) {
            throw new ConfigIoException(ioError);
        }
        catch (SerializationException serializationError) {
            throw new ConfigInvalidException(serializationError);
        }
    }

    private void writeConfig(ConfigType type, Object value, Class clazz) {
   
        try (Writer writer = fileManager.getConfigWriter(type)) {
            serializer.serialize(value, clazz, writer);
        }
        catch (IOException ioError) {
            throw new ConfigIoException(ioError);
        }
        catch (SerializationException serializationError) {
            throw new ConfigInvalidException(serializationError);
        }
    }

    private void signalUpdate() {
        updateFlag.notifyObservers();
    }

    private void notifyObservers() {
        for (ConfigObserver observer: observers) {
            observer.configurationUpdated();
        }
    }

    private class ConfigurationObserver implements Observer {
        @Override
        public void update(Observable observable, Object argument) {
            writeConfig();
            notifyObservers();
        }
    }
}
