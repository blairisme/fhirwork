package fhirconverter.converter;
import static org.junit.Assert.assertNotNull;

import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Ignore //(Blair) Test broken when project inherited from previous team. TODO: Fix
public class ObservationFHIRTests{
 	private	Logger LOGGER = LogManager.getLogger(ObservationFHIRTests.class);
	@Test
	public void getObservationIntegrationTest() throws Exception{
		ObservationFHIR observationFHIR = new ObservationFHIR();
		ArrayList<String> searchParam = new ArrayList<>();
		searchParam.add("3141-9");
		searchParam.add("8302-2");
		searchParam.add("37362-1");
		List<Observation> observations = observationFHIR.search("1", searchParam);	
		FhirContext ctx = FhirContext.forDstu2();
		for(Observation observation : observations){
			String resourceJson = ctx.newJsonParser().encodeResourceToString(observation);	
			JSONObject obtained_object = new JSONObject(resourceJson);	
			LOGGER.info(obtained_object.toString(3));	
			assertNotNull(observation);	
		} 
	}
}







