/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping.query;

import org.ucl.fhirwork.configuration.ConfigService;
import org.ucl.fhirwork.configuration.data.ConfigType;
import org.ucl.fhirwork.configuration.data.MappingConfig;
import org.ucl.fhirwork.configuration.data.ScriptedMappingConfig;
import org.ucl.fhirwork.configuration.data.BasicMappingConfig;
import org.ucl.fhirwork.mapping.query.scripted.ScriptedMapping;
import org.ucl.fhirwork.mapping.query.basic.BasicMapping;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MappingService
{
    private ConfigService configuration;
    private Provider<BasicMapping> basicMappingProvider;
    private Provider<ScriptedMapping> scriptedMappingProvider;

    @Inject
    public MappingService(
        ConfigService configuration,
        Provider<BasicMapping> basicMappingProvider,
        Provider<ScriptedMapping> scriptedMappingProvider)
    {
        this.configuration = configuration;
        this.basicMappingProvider = basicMappingProvider;
        this.scriptedMappingProvider = scriptedMappingProvider;
    }

    public boolean isSupported(String code)
    {
        MappingConfig mappingConfig = configuration.getConfig(ConfigType.Mapping);
        Map<String, BasicMappingConfig> basic = mappingConfig.getBasic();
        Map<String, ScriptedMappingConfig> scripted = mappingConfig.getScripted();
        return basic.containsKey(code) || scripted.containsKey(code);
    }

    public Collection<String> getSupported()
    {
        MappingConfig mappingConfig = configuration.getConfig(ConfigType.Mapping);
        Map<String, BasicMappingConfig> basic = mappingConfig.getBasic();
        Map<String, ScriptedMappingConfig> scripted = mappingConfig.getScripted();

        List<String> supported = new ArrayList<>();
        supported.addAll(basic.keySet());
        supported.addAll(scripted.keySet());

        return supported;
    }

    public MappingProvider getMappingProvider(String code)
    {
        MappingConfig mappingConfig = configuration.getConfig(ConfigType.Mapping);
        Map<String, BasicMappingConfig> basicConfig = mappingConfig.getBasic();
        Map<String, ScriptedMappingConfig> scriptedConfig = mappingConfig.getScripted();

        if (basicConfig.containsKey(code)){
            BasicMapping basicMapping = basicMappingProvider.get();
            basicMapping.setConfiguration(basicConfig.get(code));
            return basicMapping;
        }
        if (scriptedConfig.containsKey(code)){
            ScriptedMapping scriptedMapping = scriptedMappingProvider.get();
            scriptedMapping.setConfiguration(scriptedConfig.get(code));
            return scriptedMapping;
        }
        throw new UnsupportedOperationException();
    }
}
