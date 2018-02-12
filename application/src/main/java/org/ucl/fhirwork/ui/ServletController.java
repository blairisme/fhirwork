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

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.ucl.fhirwork.ApplicationService;
import org.ucl.fhirwork.configuration.ConfigService;
import org.ucl.fhirwork.configuration.NetworkConfigData;
import org.ucl.fhirwork.configuration.NetworkConfigType;

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

    @RequestMapping(value = "/mapping", method = RequestMethod.GET)
    public String mapping(ModelMap model)
    {
        model.addAttribute("greeting", "Hello World Again, from Spring 4 MVC");
        return "mapping";
    }

    @RequestMapping(value = "/network", method = RequestMethod.GET)
    public String network(ModelMap model)
    {
        model.addAttribute("empi", configuration.getNetworkConfig(NetworkConfigType.Empi));
        return "network";
    }

    @RequestMapping(value = "/network", method = RequestMethod.POST)
    public String networkSubmit(@ModelAttribute NetworkConfigData config, ModelMap model)
    {
        configuration.setNetworkConfig(NetworkConfigType.Empi, config);
        model.addAttribute("empi", config);
        return "network";
    }
}