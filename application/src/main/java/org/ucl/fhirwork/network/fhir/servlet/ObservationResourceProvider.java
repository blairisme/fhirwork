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
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.TokenOrListParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.InternalErrorException;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.ucl.fhirwork.ApplicationService;
import org.ucl.fhirwork.network.fhir.data.SearchParameter;
import org.ucl.fhirwork.network.fhir.data.SearchParameterBuilder;
import org.ucl.fhirwork.network.fhir.operations.observation.ReadObservationOperation;
import org.ucl.fhirwork.network.fhir.operations.patient.ReadPatientOperation;

import javax.inject.Inject;
import java.util.List;

/**
 * Instances of this class provide implement functions defined in the FHIR
 * specification related to Observations. Once implemented these operation can
 * be then be called by FHIR clients.
 *
 * @author Blair Butterworth
 */
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

    @Search
    @SuppressWarnings("unchecked")
    public List<Observation> searchObservation(
            @RequiredParam(name = Observation.SP_CODE) TokenOrListParam codes,
            @RequiredParam(name = Observation.SP_PATIENT) ReferenceParam patient)
    {
        try {
            ReadObservationOperation operation = new ReadObservationOperation();
            return (List<Observation>)applicationService.execute(operation);
        }
        catch (Throwable error) {
            throw new InternalErrorException(error);
        }
    }
}
