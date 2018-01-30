/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.fhir.servlet;

import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.TokenOrListParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.ucl.fhirwork.ApplicationService;

import javax.inject.Inject;
import java.util.List;

@SuppressWarnings("unused")
public class ObservationResourceProvider implements IResourceProvider
{
    private ApplicationService applicationService;

    @Inject
    public ObservationResourceProvider(ApplicationService applicationService)
    {
        this.applicationService = applicationService;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType()
    {
        return Observation.class;
    }

    @Search()
    public List<Observation> searchObservation(
            @RequiredParam(name = Observation.SP_CODE) TokenOrListParam codes,
            @RequiredParam(name = Observation.SP_PATIENT) ReferenceParam patient) {

        throw new UnsupportedOperationException();
    }
}
