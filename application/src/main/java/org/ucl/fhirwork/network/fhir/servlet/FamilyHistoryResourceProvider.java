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

import ca.uhn.fhir.model.dstu2.resource.FamilyMemberHistory;
import ca.uhn.fhir.rest.annotation.OptionalParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.ucl.fhirwork.mapping.ExecutorService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class provide implement functions defined in the FHIR
 * specification related to family member histories. Once implemented these
 * operation can be then be called by FHIR clients.
 *
 * @author Blair Butterworth
 */
@SuppressWarnings("unused")
public class FamilyHistoryResourceProvider implements IResourceProvider
{
    private ExecutorService executorService;

    @Inject
    public FamilyHistoryResourceProvider(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return FamilyMemberHistory.class;
    }

    @Search
    public List<FamilyMemberHistory> search(@OptionalParam(name=FamilyMemberHistory.SP_PATIENT)ReferenceParam patient)
    {
        List<FamilyMemberHistory> familyMemberHistories = new ArrayList<FamilyMemberHistory>();
        return familyMemberHistories;
    }
}