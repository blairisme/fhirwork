package fhirconverter.fhirservlet;

import ca.uhn.fhir.model.base.composite.BaseCodingDt;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.TokenOrListParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.InternalErrorException;
import fhirconverter.converter.ObservationFHIR;
import fhirconverter.exceptions.IdNotObtainedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hl7.fhir.instance.model.api.IBaseResource;

import java.util.ArrayList;
import java.util.List;

public class RestfulObservationResourceProvider implements IResourceProvider {
    private static Logger LOGGER = LogManager.getLogger(RestfulObservationResourceProvider.class);

    private ObservationFHIR observationFHIR = new ObservationFHIR();

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Observation.class;
    }

    @Search()
    public List<Observation> searchObservation(@RequiredParam(name = Observation.SP_CODE) TokenOrListParam observationCode,
                                       @RequiredParam(name = Observation.SP_PATIENT) ReferenceParam patient) {

       List<BaseCodingDt> codingList = observationCode.getListAsCodings();
       ArrayList<String> loincCodes = new ArrayList<>();
       if(codingList != null) {
           for (BaseCodingDt coding : codingList) {
               LOGGER.info("Coding: " + coding.getCodeElement().getValue());
               loincCodes.add(coding.getCodeElement().getValue());
           }
       }

        String patientId = patient.getValue();

        LOGGER.info("Patient ID: " + patientId);
        
        try{
            List<Observation> observations = observationFHIR.search(patientId, loincCodes);
            return observations;
        }
        catch (IdNotObtainedException e) {
            LOGGER.info("Id not Obtained: " + e.getMessage());
            return new ArrayList<Observation>();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new InternalErrorException(e.getMessage());
        }
    }
}
