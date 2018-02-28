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

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ucl.fhirwork.ApplicationService;
import org.ucl.fhirwork.configuration.*;
import org.ucl.fhirwork.configuration.data.*;
import org.ucl.fhirwork.mapping.query.scripted.ScriptedMapping;

@Controller
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

    @ResponseBody
    @RequestMapping(value = "/mapping/content", method = RequestMethod.GET)
    public ArrayList<String> mappingConfigContent(@RequestParam("loinc") String loinc, ModelMap model)
    {
    	MappingConfig mappingConfig = configuration.getConfig(ConfigType.Mapping);
        Map<String, BasicMappingConfig> simpleMapping = mappingConfig.getBasic();
        BasicMappingConfig mappingConfigData = simpleMapping.get(loinc);

    	ArrayList<String> data = new ArrayList<String>();
    	data.add(mappingConfigData.getText());
    	data.add(mappingConfigData.getArchetype());
    	data.add(mappingConfigData.getDate());
    	data.add(mappingConfigData.getMagnitude());
    	data.add(mappingConfigData.getUnit());
    	return data;
    }
    
    @RequestMapping(value = "/mapping", method = RequestMethod.GET)
    public String mapping(ModelMap model)
    {       
    	MappingConfig mappingConfig = configuration.getConfig(ConfigType.Mapping);
    	initializeMappingModelMap(mappingConfig, model);
    	
        return "mapping";
    }
    
    @RequestMapping(value = "/mapping", method = RequestMethod.POST)
    public String mappingSubmit(@ModelAttribute BasicMappingConfig data, @RequestParam("CurrentLoinc") String code,ModelMap model)
    {
    	MappingConfig mappingConfig = configuration.getConfig(ConfigType.Mapping);
        initializeMappingModelMap(mappingConfig, model);

        Map<String, BasicMappingConfig> basicConfig = mappingConfig.getBasic();
        basicConfig.put(code, data);

        configuration.setConfig(ConfigType.Mapping, mappingConfig);
        return "mapping";
    }
    
    private void initializeMappingModelMap(MappingConfig mappingConfig, ModelMap model){
        Map<String, BasicMappingConfig> basicConfig = mappingConfig.getBasic();
        model.addAttribute("allLoinc", basicConfig.keySet());
        model.addAttribute("LoincData", new BasicMappingConfig("", "", "", "", "", ""));
        model.addAttribute("CurrentLoinc", "");
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

        return mappingList(model);
    }

    @RequestMapping(value = "/mapping/edit/scripted", method = RequestMethod.POST)
    public String mappingEditScripted(@ModelAttribute ScriptedMappingConfig data, ModelMap model)
    {
        MappingConfig mappingConfig = configuration.getConfig(ConfigType.Mapping);
        Map<String, ScriptedMappingConfig> scriptedConfig = mappingConfig.getScripted();
        scriptedConfig.put(data.getCode(), data);
        configuration.setConfig(ConfigType.Mapping, mappingConfig);

        return mappingList(model);
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

        return mappingList(model);
    }

    @RequestMapping(value = "/mapping/new/scripted", method = RequestMethod.POST)
    public String mappingNewScripted(@ModelAttribute ScriptedMappingConfig data, ModelMap model)
    {
        MappingConfig mappingConfig = configuration.getConfig(ConfigType.Mapping);
        Map<String, ScriptedMappingConfig> scriptedConfig = mappingConfig.getScripted();
        scriptedConfig.put(data.getCode(), data);
        configuration.setConfig(ConfigType.Mapping, mappingConfig);

        return mappingList(model);
    }
}