package fhirconverter.converter;
import static org.junit.Assert.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;

import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.json.JSONArray;

public class ConversionFHIRToOpenEmpiTests {

	@Test
	public void conversionToOpenEmpiTest() throws Exception {
		JsonNode fhirResource = JsonLoader.fromPath("resource/ResourceFHIR.json");		
		JSONObject patient = new JSONObject(fhirResource.toString());
		ConversionFHIRToOpenEmpi converter = new ConversionFHIRToOpenEmpi();
		JSONObject resourceOpenEmpiFormat = converter.conversionToOpenEMPI(patient);
		System.out.println(resourceOpenEmpiFormat.toString());
		
		/* -- OpenEMPI format -- */
		ObjectMapper mapper = new ObjectMapper();
		JsonNode resultJackson = mapper.readTree(resourceOpenEmpiFormat.toString());		
		boolean fhirSchemeRequirements = Utils.validateScheme(resultJackson, "resource/openempiSchema.json");		
		assertTrue("not in openEMPI Format!", fhirSchemeRequirements);

		/* -- Name -- */
		String expectedFamilyName = getName(patient, 0);
		String expectedGivenName = getName(patient, 1);
		String expectedMaidenName = getName(patient, 2);
		String expectedMiddle = getName(patient, 3);
		assertEquals("Family name is not correct " , expectedFamilyName, resourceOpenEmpiFormat.optString("familyName"));
		assertEquals("Given name is not correct " , expectedGivenName, resourceOpenEmpiFormat.optString("givenName"));
		assertEquals("Maiden name is not correct " , expectedMaidenName, resourceOpenEmpiFormat.optString("mothersMaidenName"));
		assertEquals("Middle name is not correct " , expectedMiddle, resourceOpenEmpiFormat.optString("middleName"));
		
		/* -- Address -- */
		JSONObject address = patient.getJSONArray("address").getJSONObject(0);
		String expectedAddress1 = address.getJSONArray("line").optString(0);
		
		String expectedAddress2 = "";
		if(address.getJSONArray("line").length()>1)
			expectedAddress2=address.getJSONArray("line").optString(1);
		String expectedCity=address.optString("city");
		String expectedCountry=address.optString("country");
		String expectedState=address.optString("state");
		String expectedPostalCode = address.optString("postalCode");
		
		assertEquals("Address1 is not correct " , expectedAddress1, resourceOpenEmpiFormat.optString("address1"));
		assertEquals("Address2 is not correct " , expectedAddress2, resourceOpenEmpiFormat.optString("address2"));
		assertEquals("City is not correct " , expectedCity, resourceOpenEmpiFormat.optString("city"));
		assertEquals("Country is not correct " , expectedCountry, resourceOpenEmpiFormat.optString("country"));
		assertEquals("State is not correct " , expectedState, resourceOpenEmpiFormat.optString("state"));
		assertEquals("PostalCode is not correct " , expectedPostalCode, resourceOpenEmpiFormat.optString("postalCode"));

		/* -- Date of Birth -- */
		assertEquals("Birth date is not correct ", patient.optString("birthDate"),resourceOpenEmpiFormat.optString("dateOfBirth"));		
		
		
	}
	
	public String getName(JSONObject patient, int code) {
		String expectedFamilyName = "";
		String expectedGivenName = "";
		String expectedMaidenName = "";
		String expectedMiddle = "";
		
		JSONArray names = patient.getJSONArray("name");
		for(int i=0; i<names.length(); i++) {
			if("usual".equals(names.getJSONObject(i).optString("use"))) {
				expectedFamilyName = names.getJSONObject(i).getJSONArray("family").getString(0);
				if(names.getJSONObject(i).getJSONArray("given").length()>1) {
					expectedMiddle = names.getJSONObject(i).getJSONArray("given").getString(1);							
				} 
				expectedGivenName = names.getJSONObject(i).getJSONArray("given").getString(0);
			}
			if("maiden".equals(names.getJSONObject(i).optString("use"))) {
				expectedMaidenName = names.getJSONObject(i).getJSONArray("family").getString(0);
			}
			
		}
		if(code==0)
			return expectedFamilyName;
		if(code==1)
			return expectedGivenName;
		if(code==2)
			return expectedMaidenName;
		else
			return expectedMiddle;
		
	}
	
	
	
	
	
}
