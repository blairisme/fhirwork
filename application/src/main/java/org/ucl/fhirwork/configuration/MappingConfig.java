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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Instances of this class represent a container for all persisted mapping
 * configuration data.
 *
 * @author Chenghui Fan
 * @author Blair Butterworth
 */
public class MappingConfig
{
    public List<MappingConfigData> mappings;

    public MappingConfig(List<MappingConfigData> mappings) {
        this.mappings = mappings;
    }

    public List<MappingConfigData> getMappings() {
        return mappings;
    }

    public MappingConfigData get(String loinc){
        for (MappingConfigData mapping: mappings){
            if (Objects.equals(mapping.getLoinc(), loinc)){
                return mapping;
            }
        }
        throw new ConfigMissingException(loinc);
    }

    public MappingConfig set(String loinc, MappingConfigData config){
        List<MappingConfigData> updatedMappings = new ArrayList<>();
        updatedMappings.add(config);

        for (MappingConfigData mapping: mappings){
            if (! Objects.equals(mapping.getLoinc(), loinc)){
                updatedMappings.add(mapping);
            }
        }
        return new MappingConfig(updatedMappings);
    }
}
