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
    public Map<String, MappingConfigData> mappings;

    public MappingConfig(Map<String, MappingConfigData> mappings) {
        this.mappings = mappings;
    }

    public Map<String, MappingConfigData> getMappings() {
        return mappings;
    }

    public MappingConfigData get(String loinc){
        MappingConfigData result = mappings.get(loinc);
        if (result != null) {
            return result;
        }
        throw new ConfigMissingException(loinc);
    }

    public MappingConfig set(String loinc, MappingConfigData config) {
        Map<String, MappingConfigData> newMappings = new HashMap<>();
        newMappings.putAll(mappings);
        newMappings.put(loinc, config);
        return new MappingConfig(newMappings);
    }
}
