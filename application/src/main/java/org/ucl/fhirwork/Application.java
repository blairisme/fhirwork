/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.narrative.DefaultThymeleafNarrativeGenerator;
import ca.uhn.fhir.narrative.INarrativeGenerator;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.interceptor.CorsInterceptor;
import ca.uhn.fhir.rest.server.interceptor.ResponseHighlighterInterceptor;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.springframework.web.cors.CorsConfiguration;
import org.ucl.fhirwork.network.fhir.servlet.ObservationResourceProvider;
import org.ucl.fhirwork.network.fhir.servlet.PatientResourceProvider;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Instances of this class configure the system as a FHIR server.
 *
 * @author Blair Butterworth
 * TODO: Attribute to previous team
 */
@WebServlet(urlPatterns= {"/fhir/*"}, displayName="FHIR Server")
public class Application extends RestfulServer
{
    public Application()
    {
        super(FhirContext.forDstu2());
    }

    @Override
    public void initialize()
    {
        setResourceProviders();
        enableDescriptiveFhirErrors();
        enableCrossOriginScripting();
        enableOutputSyntaxHighlighting();
    }

    private void setResourceProviders()
    {
        Injector injector = Guice.createInjector(new ApplicationModule());

        List<IResourceProvider> providers = new ArrayList<IResourceProvider>();
        providers.add(injector.getInstance(PatientResourceProvider.class));
        providers.add(injector.getInstance(ObservationResourceProvider.class));

        setResourceProviders(providers);
    }

    private void enableDescriptiveFhirErrors()
    {
        INarrativeGenerator narrativeGen = new DefaultThymeleafNarrativeGenerator();
        getFhirContext().setNarrativeGenerator(narrativeGen);
    }

    private void enableOutputSyntaxHighlighting()
    {
        registerInterceptor(new ResponseHighlighterInterceptor());
        setDefaultPrettyPrint(true);
    }

    private void enableCrossOriginScripting()
    {
        CorsConfiguration config = new CorsConfiguration();
        CorsInterceptor corsInterceptor = new CorsInterceptor(config);
        config.addAllowedHeader("Accept");
        config.addAllowedHeader("Content-Type");
        config.addAllowedOrigin("*");
        config.addExposedHeader("Location");
        config.addExposedHeader("Content-Location");
        config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        registerInterceptor(corsInterceptor);
    }
}
