package fhirconverter.converter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import fhirconverter.exceptions.FhirSchemeNotMetException;
import fhirconverter.exceptions.ResourceNotFoundException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.List;

@Ignore //(Blair) Test broken when project inherited from previous team. TODO: Fix
public class PatientTestCases{
    
	final private String searchParameters = "{\r\n" + 
			"  \"name\": \"Kathrin\",\r\n" + 
			"}";
	
	
	
	@Test
	public void testPatientSearch() throws Exception {
		PatientFHIR tester = new PatientFHIR();	
		JsonNode fhirResource = JsonLoader.fromPath("resource/ResourceFHIR.json");		
		JSONObject patient = new JSONObject(fhirResource.toString());
		JSONObject expected = patient;
		expected.remove("meta");
		String death = expected.optString("deceasedDateTime");
		if(expected.has("deceasedDateTime")) {
			expected.remove("deceasedDateTime");
			expected.put("deceasedDateTime", death.substring(0,19) + "Z");
		}
		String newRecordID = tester.create(patient);
		JSONObject parameters = new JSONObject(searchParameters);

		/* patient model to JSONObject */
		List<Patient> p = tester.search(parameters);
		FhirContext ctx = FhirContext.forDstu2();
		String resourceJson = ctx.newJsonParser().encodeResourceToString(p.get(0));	
		JSONObject obtained_object = new JSONObject(resourceJson);
		
		
		
		//JSONObject resultSearch = obtained_object.getJSONArray("entry").getJSONObject(0).getJSONObject("resource");
		obtained_object.remove("id");
		obtained_object.remove("meta");					
        	OpenEMPIConnector delete = new OpenEMPIConnector();
		System.out.println(newRecordID+ "FOCUS HERE");
        	delete.removePersonById(newRecordID);
        	Assert.assertEquals("Search operation failed \n",expected.toString(), obtained_object.toString());
	}
	@Test
	public void testPatientUpdate() throws ResourceNotFoundException, Exception {
		PatientFHIR tester = new PatientFHIR();	
        	OpenEMPIConnector delete = new OpenEMPIConnector();
		JsonNode fhirResource = JsonLoader.fromPath("resource/ResourceFHIR.json");		
		JSONObject patient = new JSONObject(fhirResource.toString());
  		String newRecordID = tester.create(patient);
		JsonNode fhirResourceUpdate = JsonLoader.fromPath("resource/updateResourceFhir.json");		
		JSONObject updateCreate = new JSONObject(fhirResourceUpdate.toString());
		String replyExists = tester.update(newRecordID, updateCreate);
        	delete.removePersonById(newRecordID);
		assertEquals("Update Operation if the record exists failed: ", "Updated", replyExists );		

	}
	@Test(expected = FhirSchemeNotMetException.class)
	public void testPatientPatchPathNotExist() throws Exception{
		PatientFHIR tester = new PatientFHIR();
		final String jsonPatchTest = "[ { \"op\": \"replace\", \"path\": \"/gender\", \"value\": \"male\" }, {\"op\": \"add\", \"path\": \"/what is this\", \"value\": \"male\" } ]";
		ObjectMapper mapper = new ObjectMapper();
		JsonNode patchNode = mapper.readTree(jsonPatchTest);
		final JsonPatch patch = JsonPatch.fromJson(patchNode);
		OpenEMPIConnector delete = new OpenEMPIConnector();
		JsonNode fhirResource = JsonLoader.fromPath("resource/ResourceFHIR.json");		
		JSONObject patient = new JSONObject(fhirResource.toString());
  		String newRecordID = tester.create(patient);
		try{
			tester.patch(newRecordID,patch);
		}finally{
			delete.removePersonById(newRecordID);
		}
	}
	@Test(expected = JsonPatchException.class)
	public void testPatientPatchOperatorsNotExist() throws Exception{
		PatientFHIR tester = new PatientFHIR();
		final String jsonPatchTest = "[ { \"op\": \"replace\", \"path\": \"/gende\", \"value\": \"male\" } ]";
		ObjectMapper mapper = new ObjectMapper();
		JsonNode patchNode = mapper.readTree(jsonPatchTest);
		final JsonPatch patch = JsonPatch.fromJson(patchNode);
		OpenEMPIConnector delete = new OpenEMPIConnector();
		JsonNode fhirResource = JsonLoader.fromPath("resource/ResourceFHIR.json");		
		JSONObject patient = new JSONObject(fhirResource.toString());
  		String newRecordID = tester.create(patient);
		try{
			tester.patch(newRecordID,patch);
		}finally{
		       delete.removePersonById(newRecordID);
		}
	}
	@Test
	public void testPatientPatchRecord() throws Exception{
		PatientFHIR tester = new PatientFHIR();
		final String jsonPatchTest = "[ { \"op\": \"replace\", \"path\": \"/gender\", \"value\": \"male\" } ]";
		ObjectMapper mapper = new ObjectMapper();
		JsonNode patchNode = mapper.readTree(jsonPatchTest);
		final JsonPatch patch = JsonPatch.fromJson(patchNode);
		OpenEMPIConnector delete = new OpenEMPIConnector();
		JsonNode fhirResource = JsonLoader.fromPath("resource/ResourceFHIR.json");		
		JSONObject patient = new JSONObject(fhirResource.toString());
  		String newRecordID = tester.create(patient);
		try{
			assertEquals(tester.patch(newRecordID,patch), "Updated");
		}finally{
		      	delete.removePersonById(newRecordID);
		}
	}
	/*
	@Test
	public void testPatientRead() throws Exception {
		PatientFHIR tester = new PatientFHIR();
		JSONObject expected = new JSONObject(Record);
		JSONObject obtained = tester.read("3");
		Assert.assertEquals("Read operation failed: \nRead Result: \n" + obtained.toString() + " \n" + expected.toString() , expected.toString(), obtained.toString());		
	} 
	@Test
	public void testPatientCreate() throws Exception {
		PatientFHIR tester = new PatientFHIR();	
		JSONObject create = new JSONObject(createPatient);
		
		
		String newRecordID = tester.create(create);
		JSONObject exists = tester.read(newRecordID);
		
        OpenEMPIbase delete = new OpenEMPIbase();
        delete.commonRemovePersonById(newRecordID);
		exists.remove("meta");
		create.remove("meta");
		exists.remove("id");

		Assert.assertEquals("Create operation failed: \nCreate Result: \n" + exists.toString() + " \n" + create.toString() , exists.toString(), create.toString());		
	}*/
	@Test
	public void testPatientDelete() {
	}
}
