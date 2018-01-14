package fhirconverter.fhirservlet;

import ca.uhn.fhir.model.dstu2.resource.FamilyMemberHistory;
import ca.uhn.fhir.rest.annotation.OptionalParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hl7.fhir.instance.model.api.IBaseResource;

import java.util.ArrayList;
import java.util.List;

public class RestfulFamilyMemberHistoryResourceProvider implements IResourceProvider {
    private static Logger LOGGER = LogManager.getLogger(RestfulFamilyMemberHistoryResourceProvider.class);

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return FamilyMemberHistory.class;
    }

    @Search()
    public List<FamilyMemberHistory> searchFamilyMember(@OptionalParam(name = FamilyMemberHistory.SP_PATIENT) ReferenceParam patient) {

        LOGGER.info("Patient ID: " + patient);

        List<FamilyMemberHistory> familyMemberHistories = new ArrayList<FamilyMemberHistory>();
        return familyMemberHistories;
    }
}
