package fhirconverter.converter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import fhirconverter.exceptions.ResourceNotFoundException;

public class ConversionOpenEmpiToFHIRTests {
	@Test
	public void conversionTest() throws ResourceNotFoundException, Exception {
		ConversionOpenEmpiToFHIR tester = new ConversionOpenEmpiToFHIR();
		URL resourceUrl = Thread.currentThread().getContextClassLoader().getResource("ResourceOpenEMPI.xml");
		File resourceFile = new File(resourceUrl.toURI());
		String openEmpiResource = new String(Files.readAllBytes(resourceFile.toPath()), "UTF-8");
		JSONObject inputJson = XML.toJSONObject(openEmpiResource);
		
		List<Patient> p = tester.conversion(openEmpiResource);
		
		/* patient model to JSONObject */
		FhirContext ctx = FhirContext.forDstu2();
		String resourceJson = ctx.newJsonParser().encodeResourceToString(p.get(0));	
		JSONObject resourceFhir = new JSONObject(resourceJson);
		
		/* -- FHIR format -- */
		ObjectMapper mapper = new ObjectMapper();
		JsonNode resultJackson = mapper.readTree(resourceFhir.toString());
        URL schemaUrl = Thread.currentThread().getContextClassLoader().getResource("Patient.schema.json");
		boolean fhirSchemeRequirements = Utils.validateScheme(resultJackson, schemaUrl.getPath());
		assertTrue("not in FHIR Format!", fhirSchemeRequirements);

		/* -- Name -- */
		OpenEmpiFHIRConverterTestHelper helper = new OpenEmpiFHIRConverterTestHelper();
		String obtainedFamilyName = helper.getName(resourceFhir, OpenEmpiFHIRConverterTestHelper.FAMILYNAME);
		String obtainedGivenName = helper.getName(resourceFhir, OpenEmpiFHIRConverterTestHelper.GIVENNAME);
		String obtainedMaidenName = helper.getName(resourceFhir, OpenEmpiFHIRConverterTestHelper.MAIDENNAME);
		String obtainedMiddle = helper.getName(resourceFhir, OpenEmpiFHIRConverterTestHelper.MIDDLE);
		assertEquals("Family name is not correct " ,  inputJson.getJSONObject("person").optString("familyName"), obtainedFamilyName);
		assertEquals("Given name is not correct " , inputJson.getJSONObject("person").optString("givenName"), obtainedGivenName);
		assertEquals("Maiden name is not correct " , inputJson.getJSONObject("person").optString("mothersMaidenName"), obtainedMaidenName);
		assertEquals("Middle name is not correct " , inputJson.getJSONObject("person").optString("middleName"), obtainedMiddle);

		
		/* -- Address -- */
		JSONObject address = resourceFhir.getJSONArray("address").getJSONObject(0);
		String obtainedAddress1 = address.getJSONArray("line").optString(0);
		
		String obtainedAddress2 = "";
		if(address.getJSONArray("line").length()>1)
			obtainedAddress2=address.getJSONArray("line").optString(1);
		String obtainedCity=address.optString("city");
		String obtainedCountry=address.optString("country");
		String obtainedState=address.optString("state");
		String obtainedPostalCode = address.optString("postalCode");
		
		assertEquals("Address1 is not correct " , inputJson.getJSONObject("person").optString("address1"), obtainedAddress1 );
		assertEquals("Address2 is not correct " , inputJson.getJSONObject("person").optString("address2"), obtainedAddress2 );
		assertEquals("City is not correct " , inputJson.getJSONObject("person").optString("city"), obtainedCity);
		assertEquals("Country is not correct " , inputJson.getJSONObject("person").optString("country"), obtainedCountry);
		assertEquals("State is not correct " , inputJson.getJSONObject("person").optString("state"), obtainedState);
		assertEquals("PostalCode is not correct " , inputJson.getJSONObject("person").optString("postalCode"), obtainedPostalCode);

		/* -- Date of Birth -- */
		assertEquals("Birth date is not correct ", inputJson.getJSONObject("person").optString("dateOfBirth").substring(0, 10), resourceFhir.optString("birthDate"));		
		
		
		/* -- PersonID -- */
		assertTrue("no Id included ", resourceFhir.has("id"));
		assertEquals("ID is not correct " , inputJson.getJSONObject("person").optString("personId"),resourceFhir.optString("id") );
		
		/* -- PersonIdentifier -- */
		assertTrue("No person Identifier Included " ,resourceFhir.has("identifier"));		
		
		boolean identifierOpenEMPI = identifiersTest(inputJson, resourceFhir,0 );
		boolean correctIdentifiers = identifiersTest(inputJson, resourceFhir,1);
				
		assertFalse("The result should not contain OpenEMPI identifier",identifierOpenEMPI);
		assertTrue("The person identifiers are not correct",correctIdentifiers);

	}
	
	private static boolean identifiersTest(JSONObject inputJson, JSONObject resourceFhir, int code) {
		boolean identifierOpenEMPI = false;
		boolean correctIdentifiers = true;

		JSONArray identifiers = resourceFhir.getJSONArray("identifier");
		HashMap<String, String> retrievedIdentifiers = new HashMap<String, String>();
		
		for(int i=0; i<identifiers.length(); i++) {
			String system = identifiers.getJSONObject(i).optString("system");
			if("OpenEMPI".equals(system))
				identifierOpenEMPI = true;	
			else
				retrievedIdentifiers.put(identifiers.getJSONObject(i).optString("system"), identifiers.getJSONObject(i).optString("value"));
		}

		if(code == 1) {
			JSONArray openEmpiIdentifiers = inputJson.getJSONObject("person").getJSONArray("personIdentifiers");
			for(int i=0; i<openEmpiIdentifiers.length(); i++) {
				String value = openEmpiIdentifiers.getJSONObject(i).optString("identifier");
				String system = openEmpiIdentifiers.getJSONObject(i).getJSONObject("identifierDomain").optString("identifierDomainName");
				System.out.println(system + " => " + value); 
				if(!"OpenEMPI".equals(system)) {
					if(!(retrievedIdentifiers.containsKey(system)&&retrievedIdentifiers.get(system).equals(value))) {
						correctIdentifiers = false;
					}else
					if(retrievedIdentifiers.containsKey(system))
						retrievedIdentifiers.remove(system);
				}
			}
			if(!retrievedIdentifiers.isEmpty())
				correctIdentifiers = false;
			return correctIdentifiers;
		}
		else
			return identifierOpenEMPI;
	}
	
}
