package fhirconverter.converter;
import static org.junit.Assert.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;

import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ConversionFHIRToOpenEmpiTests {
	private JSONObject resourcePatient;
	private ConversionFHIRToOpenEmpi converter = new ConversionFHIRToOpenEmpi();
	
	public ConversionFHIRToOpenEmpiTests(){
		URL resourceUrl = Thread.currentThread().getContextClassLoader().getResource("ResourceFHIR.json");
		try {
			JsonNode fhirResource = JsonLoader.fromFile(new File(resourceUrl.getPath()));
			resourcePatient = new JSONObject(fhirResource.toString());
		} catch (IOException e) {
			e.printStackTrace();
			resourcePatient = null;
		}
	}
	
//  Test case written by the previous team, has been divided into the following tests
//	@Test
//	public void conversionToOpenEmpiTest() throws Exception {
//
//		URL resourceUrl = Thread.currentThread().getContextClassLoader().getResource("ResourceFHIR.json");
//		JsonNode fhirResource = JsonLoader.fromFile(new File(resourceUrl.getPath()));
//		JSONObject patient = new JSONObject(fhirResource.toString());
//		ConversionFHIRToOpenEmpi converter = new ConversionFHIRToOpenEmpi();
//		JSONObject resourceOpenEmpiFormat = converter.conversionToOpenEMPI(patient);
//		System.out.println(resourceOpenEmpiFormat.toString());
//		
//		/* -- OpenEMPI format -- */
//		ObjectMapper mapper = new ObjectMapper();
//		JsonNode resultJackson = mapper.readTree(resourceOpenEmpiFormat.toString());
//        URL schemaUrl = Thread.currentThread().getContextClassLoader().getResource("openempiSchema.json");
//		boolean fhirSchemeRequirements = Utils.validateScheme(resultJackson, schemaUrl.getPath());
//		assertTrue("not in openEMPI Format!", fhirSchemeRequirements);
//
//		/* -- Name -- */
//		ConverterTestHelper helper = new ConverterTestHelper();
//		String expectedFamilyName = helper.getName(patient, ConverterTestHelper.FAMILYNAME);
//		String expectedGivenName = helper.getName(patient, ConverterTestHelper.GIVENNAME);
//		String expectedMaidenName = helper.getName(patient, ConverterTestHelper.MAIDENNAME);
//		String expectedMiddle = helper.getName(patient, ConverterTestHelper.MIDDLE);
//		assertEquals("Family name is not correct " , expectedFamilyName, resourceOpenEmpiFormat.optString("familyName"));
//		assertEquals("Given name is not correct " , expectedGivenName, resourceOpenEmpiFormat.optString("givenName"));
//		assertEquals("Maiden name is not correct " , expectedMaidenName, resourceOpenEmpiFormat.optString("mothersMaidenName"));
//		assertEquals("Middle name is not correct " , expectedMiddle, resourceOpenEmpiFormat.optString("middleName"));
//		
//		/* -- Address -- */
//		JSONObject address = patient.getJSONArray("address").getJSONObject(0);
//		String expectedAddress1 = address.getJSONArray("line").optString(0);
//		
//		String expectedAddress2 = "";
//		if(address.getJSONArray("line").length()>1)
//			expectedAddress2=address.getJSONArray("line").optString(1);
//		String expectedCity=address.optString("city");
//		String expectedCountry=address.optString("country");
//		String expectedState=address.optString("state");
//		String expectedPostalCode = address.optString("postalCode");
//		
//		assertEquals("Address1 is not correct " , expectedAddress1, resourceOpenEmpiFormat.optString("address1"));
//		assertEquals("Address2 is not correct " , expectedAddress2, resourceOpenEmpiFormat.optString("address2"));
//		assertEquals("City is not correct " , expectedCity, resourceOpenEmpiFormat.optString("city"));
//		assertEquals("Country is not correct " , expectedCountry, resourceOpenEmpiFormat.optString("country"));
//		assertEquals("State is not correct " , expectedState, resourceOpenEmpiFormat.optString("state"));
//		assertEquals("PostalCode is not correct " , expectedPostalCode, resourceOpenEmpiFormat.optString("postalCode"));
//
//		/* -- Date of Birth -- */
//		assertEquals("Birth date is not correct ", patient.optString("birthDate"),resourceOpenEmpiFormat.optString("dateOfBirth"));		
//	}
	
	@Test
	public void conversionToOpenEmpiFormatTest() throws Exception {
		JSONObject patient = new JSONObject(this.resourcePatient.toString());
		JSONObject resourceOpenEmpiFormat = this.converter.conversionToOpenEMPI(patient);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode resultJackson = mapper.readTree(resourceOpenEmpiFormat.toString());
        URL schemaUrl = Thread.currentThread().getContextClassLoader().getResource("openempiSchema.json");
		boolean fhirSchemeRequirements = Utils.validateScheme(resultJackson, schemaUrl.getPath());
		assertTrue("not in openEMPI Format!", fhirSchemeRequirements);
	}
	
	@Test
	public void setGenderTest(){
		JSONObject incorrectFormatObject = new JSONObject();
		assertTrue("Unexpected return value with invalid input", converter.setGender(incorrectFormatObject).length() == 0);
		
		JSONObject femalePatient = new JSONObject();
		femalePatient.put("gender", "female");
		JSONObject genderDetails1 = this.converter.setGender(femalePatient);
		assertEquals("Female genderCd was set incorrectly" , genderDetails1.getString("genderCd"), "1");
		
		JSONObject malePatient = new JSONObject();
		malePatient.put("gender", "male");
		JSONObject genderDetails2 = this.converter.setGender(malePatient);
		assertEquals("Male genderCode was set incorrectly" , genderDetails2.getString("genderCode"), "M");
		
		JSONObject otherGenderPatient = new JSONObject();
		otherGenderPatient.put("gender", "other");
		JSONObject genderDetails3 = this.converter.setGender(otherGenderPatient);
		assertEquals("Other gender genderDescription was set incorrectly" , genderDetails3.getString("genderDescription"), "Other");
		
		JSONObject unknownGenderPatient = new JSONObject();
		unknownGenderPatient.put("gender", "unknown");
		JSONObject genderDetails4 = this.converter.setGender(unknownGenderPatient);
		assertEquals("Unknown gender genderName was set incorrectly" , genderDetails4.getString("genderName"), "Unknown");
	}
	
	@Test
	public void createNameTest(){
		JSONObject content = new JSONObject();
		JSONObject patient = new JSONObject(this.resourcePatient.toString());
		JSONArray Namesarray = patient.optJSONArray("name");
		JSONObject details = Namesarray.getJSONObject(0);
		content = converter.createName(content,details);
		
		OpenEmpiFHIRConverterTestHelper helper = new OpenEmpiFHIRConverterTestHelper();
		String expectedFamilyName = helper.getName(patient, OpenEmpiFHIRConverterTestHelper.FAMILYNAME);
		String expectedGivenName = helper.getName(patient, OpenEmpiFHIRConverterTestHelper.GIVENNAME);
		String expectedMiddle = helper.getName(patient, OpenEmpiFHIRConverterTestHelper.MIDDLE);
		
		assertEquals("Family name is not correct " , expectedFamilyName, content.optString("familyName"));
		assertEquals("Given name is not correct " , expectedGivenName, content.optString("givenName"));
		assertEquals("Middle name is not correct " , expectedMiddle, content.optString("middleName"));
	}
	
	@Test
	public void createAddressTest(){
		JSONObject address = new JSONObject();
		JSONObject patient = new JSONObject(this.resourcePatient.toString());
		JSONObject addressResource = patient.getJSONArray("address").getJSONObject(0);
		address = this.converter.createAddress(address, addressResource);
		
		String expectedAddress1 = addressResource.getJSONArray("line").optString(0);
		String expectedAddress2 = addressResource.getJSONArray("line").optString(1);
		String expectedCity=addressResource.optString("city");
		String expectedCountry=addressResource.optString("country");
		String expectedState=addressResource.optString("state");
		String expectedPostalCode = addressResource.optString("postalCode");
		
		assertEquals("Address1 is not correct " , expectedAddress1, address.optString("address1"));
		assertEquals("Address2 is not correct " , expectedAddress2, address.optString("address2"));
		assertEquals("City is not correct " , expectedCity, address.optString("city"));
		assertEquals("Country is not correct " , expectedCountry, address.optString("country"));
		assertEquals("State is not correct " , expectedState, address.optString("state"));
		assertEquals("PostalCode is not correct " , expectedPostalCode, address.optString("postalCode"));
	}
	
	@Test
	public void setFullNamesWithInvalidInput(){
		JSONObject content = new JSONObject();
		JSONObject patient = new JSONObject(this.resourcePatient.toString());
		
		//No name found
		patient.remove("name");
		assertEquals("Unexpeted return value with invalid input", content, converter.setFullNames(patient, content));
		
		//Name not stored in JSONArray (unsupported format)
		patient.put("name", "William");
		assertEquals("Unexpeted return value with invalid input", content, converter.setFullNames(patient, content));
	}
	
	@Test
	public void setFullNamesWithValidInput(){
		OpenEmpiFHIRConverterTestHelper helper = new OpenEmpiFHIRConverterTestHelper();
		JSONObject content = new JSONObject();
		JSONObject patient = new JSONObject(this.resourcePatient.toString());
		JSONObject openEmpiFormat = converter.setFullNames(patient, content);
		
		String expectedFamilyName = helper.getName(patient, OpenEmpiFHIRConverterTestHelper.FAMILYNAME);
		String expectedGivenName = helper.getName(patient, OpenEmpiFHIRConverterTestHelper.GIVENNAME);
		String expectedMaidenName = helper.getName(patient, OpenEmpiFHIRConverterTestHelper.MAIDENNAME);
		String expectedMiddle = helper.getName(patient, OpenEmpiFHIRConverterTestHelper.MIDDLE);
		assertEquals("Family name is not correct " , expectedFamilyName, openEmpiFormat.optString("familyName"));
		assertEquals("Given name is not correct " , expectedGivenName, openEmpiFormat.optString("givenName"));
		assertEquals("Maiden name is not correct " , expectedMaidenName, openEmpiFormat.optString("mothersMaidenName"));
		assertEquals("Middle name is not correct " , expectedMiddle, openEmpiFormat.optString("middleName"));
	}

	@Test
	public void setMaritalStatusWithInvalidInput(){
		JSONObject content = new JSONObject();
		JSONObject patient = new JSONObject(this.resourcePatient.toString());
		
		patient.getJSONObject("maritalStatus").getJSONArray("coding").getJSONObject(0).remove("code");
		assertEquals("Unexpeted return value with invalid input", content, converter.setMaritalStatus(patient, content));
		
		patient = new JSONObject(this.resourcePatient.toString());
		patient.getJSONObject("maritalStatus").remove("coding");
		assertEquals("Unexpeted return value with invalid input", content, converter.setMaritalStatus(patient, content));
		
		patient = new JSONObject(this.resourcePatient.toString());
		patient.remove("maritalStatus");
		assertEquals("Unexpeted return value with invalid input", content, converter.setMaritalStatus(patient, content));
	}
	
	@Test
	public void setMaritalStatusWithValidInput(){
		JSONObject content = new JSONObject();
		JSONObject patient = new JSONObject(this.resourcePatient.toString());
		
		
		content = this.converter.setMaritalStatus(patient, content);
		assertEquals("MaritalStatus is not correct with code D", "DIVORCED", content.getString("maritalStatusCode"));
		
		patient.getJSONObject("maritalStatus").getJSONArray("coding").getJSONObject(0).remove("code");
		patient.getJSONObject("maritalStatus").getJSONArray("coding").getJSONObject(0).put("code", "Q");
		content = this.converter.setMaritalStatus(patient, content);
		assertEquals("MaritalStatus is not correct with code Q", "UNKNOWN", content.getString("maritalStatusCode"));
	}

	@Test
	public void setDeathTimeTest(){
		JSONObject content = new JSONObject();
		JSONObject patient = new JSONObject(this.resourcePatient.toString());
		
		content = this.converter.setDeathTime(patient, content);
		assertEquals("Death time is not correct", "1982-08-25T07:06:12", content.getString("deathTime"));
	
		patient.remove("deceasedDateTime");
		assertEquals("Death time is not correct with invalid input", content, this.converter.setDeathTime(patient, content));
	}

	@Test
	public void setAddressesWithInvalidInput(){
		JSONObject content = new JSONObject();
		JSONObject patient = new JSONObject(this.resourcePatient.toString());
		
		patient.remove("address");
		patient.put("address", "a");
		assertEquals("Address is not correct with invalid input", 
				content, this.converter.setAddresses(patient, content));
		
		patient = new JSONObject(this.resourcePatient.toString());
		patient.remove("address");
		assertEquals("Address is not correct with invalid input", 
				content, this.converter.setAddresses(patient, content));
	}

	@Test
	public void setTelecomTest(){
		JSONObject content = new JSONObject();
		JSONObject patient = new JSONObject(this.resourcePatient.toString());
		
		content = converter.setTelecom(patient, content);
		assertEquals("Phone number is not correct", "678975459787", content.getString("phoneNumber"));
		assertEquals("Email is not correct", "kathrinwilliams@gmail.com", content.getString("email"));
	
		patient.remove("telecom");
		patient.put("telecom", "a");
		content = new JSONObject();
		assertEquals("Telecom is not correct with invalid input", 
				content, this.converter.setTelecom(patient, content));
		
		patient = new JSONObject(this.resourcePatient.toString());
		patient.remove("telecom");
		content = new JSONObject();
		assertEquals("Telecom is not correct with invalid input", 
				content, this.converter.setTelecom(patient, content));
	}
	
	@Test
	public void setMetaDataTest(){
		JSONObject content = new JSONObject();
		JSONObject patient = new JSONObject(this.resourcePatient.toString());
		
		content = converter.setMetaData(patient, content);
		assertEquals("Meta data is not correct", "2017-07-19T08:17:26.188-04:00", content.getString("dateChanged"));
		
		patient.remove("meta");
		content = new JSONObject();
		assertEquals("Meta data is not correct with invalid input", 
				content, this.converter.setTelecom(patient, content));
		
		patient = new JSONObject(this.resourcePatient.toString());
		patient.getJSONObject("meta").remove("lastUpdated");
		content = new JSONObject();
		assertEquals("Meta data is not correct with invalid input", 
				content, this.converter.setTelecom(patient, content));
	}
	
	@Test
	public void setPersonIdentifierTest(){
		JSONObject content = new JSONObject();
		JSONObject patient = new JSONObject(this.resourcePatient.toString());
		
		content = converter.setPersonIdentifier(patient, content);
		assertEquals("Identifier is not correct", "568749875445698798988873", 
				(content.optJSONArray("personIdentifiers").optJSONObject(0).getString("identifier")));
		assertEquals("Identifier domain is not correct", "OpenMRS", 
				content.optJSONArray("personIdentifiers").optJSONObject(0).optJSONObject("identifierDomain").getString("identifierDomainName"));
	}
}
