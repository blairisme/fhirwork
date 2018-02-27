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
    private Map<String, BasicMappingConfig> basic;
    private Map<String, ScriptedMappingConfig> scripted;

    public MappingConfig(
        Map<String, BasicMappingConfig> basic,
        Map<String, ScriptedMappingConfig> scripted)
    {
        this.basic = basic;
        this.scripted = scripted;
    }

    public Map<String, BasicMappingConfig> getBasic() {
        return basic;
    }

    public Map<String, ScriptedMappingConfig> getScripted() {
        return scripted;
    }
}
