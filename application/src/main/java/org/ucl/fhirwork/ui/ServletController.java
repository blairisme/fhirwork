/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.ui;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.ucl.fhirwork.ApplicationService;
import org.ucl.fhirwork.configuration.ConfigService;
import org.ucl.fhirwork.configuration.data.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Instances of this class declare web pages used by the FhirWork configuration
 * user interface, and provide the business logic that allows them to operate.
 *
 * @author Blair Butterworth
 * @author Chenghui Fan
 * @author Xiaolong Chen
 */
@Controller
@Scope("session")
@RequestMapping("/")
@SuppressWarnings("unused")
public class ServletController
{
    private ConfigService configuration;

    public ServletController(){
        this(ApplicationService.instance());
    }

    public ServletController(ApplicationService applicationService) {
        this.configuration = applicationService.get(ConfigService.class);
    }

    @RequestMapping(value = "/network", method = RequestMethod.GET)
    public String network(ModelMap model)
    {
        NetworkConfig networkConfig = configuration.getConfig(ConfigType.Network);
        model.addAttribute("empi", networkConfig.getEmpi());
        return "network";
    }

    @RequestMapping(value = "/network", method = RequestMethod.POST)
    public String networkSubmit(@ModelAttribute NetworkConfigData data, ModelMap model)
    {
        NetworkConfig networkConfig = configuration.getConfig(ConfigType.Network);
        networkConfig.setEmpi(data);
        configuration.setConfig(ConfigType.Network, networkConfig);

        model.addAttribute("empi", data);
        return "network";
    }

    @RequestMapping(value = "/mapping/list", method = RequestMethod.GET)
    public String mappingList(ModelMap model)
    {
        MappingConfig mappingConfig = configuration.getConfig(ConfigType.Mapping);
        Map<String, BasicMappingConfig> basicConfig = mappingConfig.getBasic();
        Map<String, ScriptedMappingConfig> scriptConfig = mappingConfig.getScripted();

        Collection<String> mappings = new ArrayList<>();
        mappings.addAll(basicConfig.keySet());
        mappings.addAll(scriptConfig.keySet());
        model.addAttribute("mappings", mappings);

        return "mapping_list";
    }

    @RequestMapping(value = "/mapping/edit", method = RequestMethod.GET)
    public String mappingEdit(@RequestParam("code")String code, ModelMap model)
    {
        MappingConfig mappingConfig = configuration.getConfig(ConfigType.Mapping);
        Map<String, BasicMappingConfig> basicConfig = mappingConfig.getBasic();
        Map<String, ScriptedMappingConfig> scriptConfig = mappingConfig.getScripted();

        if (basicConfig.containsKey(code)){
            BasicMappingConfig config = basicConfig.get(code);
            model.addAttribute("type", "basic");
            model.addAttribute("mapping", config);
        }
        if (scriptConfig.containsKey(code)){
            ScriptedMappingConfig config = scriptConfig.get(code);
            model.addAttribute("type", "scripted");
            model.addAttribute("mapping", config);
        }
        return "mapping_edit";
    }

    @RequestMapping(value = "/mapping/edit/basic", method = RequestMethod.POST)
    public String mappingEditBasic(@ModelAttribute BasicMappingConfig data, ModelMap model)
    {
        MappingConfig mappingConfig = configuration.getConfig(ConfigType.Mapping);
        Map<String, BasicMappingConfig> basicConfig = mappingConfig.getBasic();
        basicConfig.put(data.getCode(), data);
        configuration.setConfig(ConfigType.Mapping, mappingConfig);

        return "redirect:/configuration/mapping/list";
    }

    @RequestMapping(value = "/mapping/edit/scripted", method = RequestMethod.POST)
    public String mappingEditScripted(@ModelAttribute ScriptedMappingConfig data, ModelMap model)
    {
        MappingConfig mappingConfig = configuration.getConfig(ConfigType.Mapping);
        Map<String, ScriptedMappingConfig> scriptedConfig = mappingConfig.getScripted();
        scriptedConfig.put(data.getCode(), data);
        configuration.setConfig(ConfigType.Mapping, mappingConfig);

        return "redirect:/configuration/mapping/list";
    }

    @RequestMapping(value = "/mapping/new", method = RequestMethod.GET)
    public String mappingNew(ModelMap model)
    {
        return "mapping_new";
    }

    @RequestMapping(value = "/mapping/new/basic", method = RequestMethod.POST)
    public String mappingNewBasic(@ModelAttribute BasicMappingConfig data, ModelMap model)
    {
        MappingConfig mappingConfig = configuration.getConfig(ConfigType.Mapping);
        Map<String, BasicMappingConfig> basicConfig = mappingConfig.getBasic();
        basicConfig.put(data.getCode(), data);
        configuration.setConfig(ConfigType.Mapping, mappingConfig);

        return "redirect:/configuration/mapping/list";
    }

    @RequestMapping(value = "/mapping/new/scripted", method = RequestMethod.POST)
    public String mappingNewScripted(@ModelAttribute ScriptedMappingConfig data, ModelMap model)
    {
        MappingConfig mappingConfig = configuration.getConfig(ConfigType.Mapping);
        Map<String, ScriptedMappingConfig> scriptedConfig = mappingConfig.getScripted();
        scriptedConfig.put(data.getCode(), data);
        configuration.setConfig(ConfigType.Mapping, mappingConfig);

        return "redirect:/configuration/mapping/list";
    }

    @RequestMapping(value = "/mapping/delete", method = RequestMethod.GET)
    public String mappingDelete(@RequestParam("code")String code, ModelMap model)
    {
        MappingConfig mappingConfig = configuration.getConfig(ConfigType.Mapping);

        Map<String, BasicMappingConfig> basicConfig = mappingConfig.getBasic();
        Map<String, ScriptedMappingConfig> scriptedConfig = mappingConfig.getScripted();

        basicConfig.remove(code);
        scriptedConfig.remove(code);

        configuration.setConfig(ConfigType.Mapping, mappingConfig);
        return "redirect:/configuration/mapping/list";
    }

    @RequestMapping(value = "/cache", method = RequestMethod.GET)
    public String cache(ModelMap model)
    {
        CacheConfig cacheConfig = configuration.getConfig(ConfigType.Cache);
        model.addAttribute("config", cacheConfig);
        return "cache";
    }

    @RequestMapping(value = "/cache", method = RequestMethod.POST)
    public String cacheChecked(@ModelAttribute CacheConfig cache, ModelMap model)
    {
        configuration.setConfig(ConfigType.Cache, cache);
        model.addAttribute("config", cache);
        return "cache";
    }
}