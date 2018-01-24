package fhirconverter.converter;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import fhirconverter.utilities.PatientHelper;

import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//@Ignore //(Blair) Test broken when project inherited from previous team. TODO: Fix
public class ObservationFHIRTests{
 	private	Logger LOGGER = LogManager.getLogger(ObservationFHIRTests.class);
	@Test
	public void getObservationIntegrationTest() throws Exception{
		JSONObject expectedJsonObj = new JSONObject();
		expectedJsonObj.put("test", "test");
		
		OpenEHRConnector openEHRconnector = Mockito.mock(OpenEHRConnector.class);
		PatientHelper patientHelper = Mockito.mock(PatientHelper.class);
		OpenEHRConvertor openEHRconvertor = Mockito.mock(OpenEHRConvertor.class);
		Mockito.when(patientHelper.retrieveNHSbyId("1")).thenReturn("2");
		Mockito.when(openEHRconnector.getEHRIdByNhsNumber("2")).thenReturn("3");
		
		String expectedAqlQuery = "select body_weight/data[at0002]/origin/value as LONIC_3141_9_date, body_weight/data[at0002]/events[at0003]/data[at0001]/items[at0004]/value/magnitude as LONIC_3141_9_magnitude, body_weight/data[at0002]/events[at0003]/data[at0001]/items[at0004]/value/units as LONIC_3141_9_units, height/data[at0001]/origin/value as LONIC_8302_2_date, height/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/magnitude as LONIC_8302_2_magnitude, height/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/units as LONIC_8302_2_units, skeletal_age/data[at0001]/origin/value as LONIC_37362_1_date, skeletal_age/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value/value as LONIC_37362_1_value from EHR [ehr_id/value='3'] contains COMPOSITION c contains ( OBSERVATION body_weight[openEHR-EHR-OBSERVATION.body_weight.v1] or OBSERVATION height[openEHR-EHR-OBSERVATION.height.v1] or OBSERVATION skeletal_age[openEHR-EHR-OBSERVATION.skeletal_age.v0] )";
		
		Mockito.when(openEHRconnector.getObservations(expectedAqlQuery)).thenReturn(expectedJsonObj);
		Mockito.when(openEHRconvertor.jsonToObservation(expectedJsonObj)).thenReturn(null);
		
		ObservationFHIR observationFHIR = new ObservationFHIR(patientHelper,openEHRconnector, openEHRconvertor);
		
		ArrayList<String> searchParam = new ArrayList<>();
		searchParam.add("3141-9");
		searchParam.add("8302-2");
		searchParam.add("37362-1");
		List<Observation> observations = observationFHIR.search("1", searchParam);	
//		FhirContext ctx = FhirContext.forDstu2();
//		for(Observation observation : observations){
//			String resourceJson = ctx.newJsonParser().encodeResourceToString(observation);	
//			JSONObject obtained_object = new JSONObject(resourceJson);	
//			LOGGER.info(obtained_object.toString(3));	
//			assertNotNull(observation);	
//		} 
		assertTrue("Not expected obeservation", observations == null);
	}
}







