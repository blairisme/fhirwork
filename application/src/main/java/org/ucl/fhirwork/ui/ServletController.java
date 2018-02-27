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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, BasicMappingConfig> simpleMapping = mappingConfig.getBasic();
        BasicMappingConfig data = new BasicMappingConfig("", "", "", "", "");
        model.addAttribute("allLoinc", simpleMapping.keySet());
        model.addAttribute("LoincData", data);
        model.addAttribute("CurrentLoinc", "");
        return "mapping";
    }
    
    @RequestMapping(value = "/mapping", method = RequestMethod.POST)
    public String mappingSubmit(@ModelAttribute BasicMappingConfig data, @RequestParam("CurrentLoinc") String code,ModelMap model)
    {
        MappingConfig mappingConfig = configuration.getConfig(ConfigType.Mapping);
        Map<String, BasicMappingConfig> simpleMapping = mappingConfig.getBasic();
        simpleMapping.put(code, data);

        configuration.setConfig(ConfigType.Mapping, mappingConfig);

        model.addAttribute("allLoinc", simpleMapping.keySet());
        model.addAttribute("LoincData", data);
        model.addAttribute("CurrentLoinc", code);
    	  
        return "mapping";
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
}