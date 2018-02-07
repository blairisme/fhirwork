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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class ServletController
{
    @RequestMapping(value = "/network", method = RequestMethod.GET)
    public String network(ModelMap model)
    {
        model.addAttribute("greeting", "Hello World from Spring 4 MVC");
        return "network";
    }

    @RequestMapping(value = "/mapping", method = RequestMethod.GET)
    public String mapping(ModelMap model)
    {
        model.addAttribute("greeting", "Hello World Again, from Spring 4 MVC");
        return "mapping";
    }
}