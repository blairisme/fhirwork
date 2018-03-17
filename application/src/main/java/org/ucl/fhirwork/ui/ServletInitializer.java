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

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.ucl.fhirwork.ApplicationService;

import java.lang.reflect.Method;

/**
 * Instances of this class configure the Spring MVC servlet engine used to
 * render the FhirWork configuration user interface.
 *
 * @author Blair Butterworth
 */
@SuppressWarnings("unused")
public class ServletInitializer implements WebApplicationInitializer
{
    public void onStartup(ServletContext container)
    {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ServletConfiguration.class);
        context.setServletContext(container);

        ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", new DispatcherServlet(context));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/configuration/*");
    }
}