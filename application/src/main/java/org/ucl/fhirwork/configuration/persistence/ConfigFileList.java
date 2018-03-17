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

import org.ucl.fhirwork.configuration.data.ConfigType;
import org.ucl.fhirwork.configuration.persistence.Environment;

import java.util.Map;

/**
 * Instances of this class represent a manifest of configuration file locations,
 * each designed for use at a different period in product development.
 * E.g., testing or production.
 *
 * @author Chenghui Fan
 * @author Blair Butterworth
 */
@SuppressWarnings("UnusedDeclaration")
public class ConfigFileList
{
    private Map<ConfigType, String> testing;
    private Map<ConfigType, String> production;
    private Map<ConfigType, String> development;

    public ConfigFileList() {
    }

    public Map<ConfigType, String> getTestingConfig() {
        return testing;
    }

    public Map<ConfigType, String> getProductionConfig() {
        return production;
    }

    public Map<ConfigType, String> getDevelopmentConfig() {
        return development;
    }

    public Map<ConfigType, String> get(Environment type){
        switch (type){
            case Testing: return testing;
            case Development: return development;
            case Production: return production;
            default: throw new IllegalArgumentException();
        }
    }
}
