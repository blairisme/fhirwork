/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.configuration.data;

import org.ucl.fhirwork.configuration.exception.ConfigMissingException;

import java.util.*;

/**
 * Instances of this class represent a container for all persisted mapping
 * configuration data.
 *
 * @author Chenghui Fan
 * @author Blair Butterworth
 */
public class MappingConfig
{
    private Map<String, MappingConfigData> mappings;

    public MappingConfig(Map<String, MappingConfigData> mappings) {
        this.mappings = mappings;
    }

    public Map<String, MappingConfigData> getMappings() {
        return mappings;
    }

    public boolean hasData(String code){
        return mappings.containsKey(code);
    }

    public MappingConfigData getData(String code){
        MappingConfigData result = mappings.get(code);
        if (result != null) {
            return result;
        }
        throw new ConfigMissingException(code);
    }

    public MappingConfig setData(String code, MappingConfigData config) {
        Map<String, MappingConfigData> newMappings = new HashMap<>();
        newMappings.putAll(mappings);
        newMappings.put(code, config);
        return new MappingConfig(newMappings);
    }

    public Collection<String> getCodes(){
    	return this.mappings.keySet();
    }
}
