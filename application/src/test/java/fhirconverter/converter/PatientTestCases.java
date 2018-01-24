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
import org.json.XML;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class PatientTestCases{
    
	final private String searchParameters = "{\r\n" + 
			"  \"name\": \"Kathrin\",\r\n" + 
			"}";
	final private String FHIRtoEMPIXML = "<person><deathTime>1982-08-25T07:06:12</deathTime><country>UK</country>"
			+ "<birthOrder>2</birthOrder><gender><genderDescription>Female</genderDescription>"
			+ "<genderCode>F</genderCode><genderCd>1</genderCd><genderName>Female</genderName></gender>"
			+ "<address2>Penton Rise</address2><city>London</city><prefix>Miss</prefix>"
			+ "<address1>Kings Cross</address1><givenName>Kathrin</givenName><postalCode>589632</postalCode>"
			+ "<dateOfBirth>1970-09-24</dateOfBirth><mothersMaidenName>Katy</mothersMaidenName>"
			+ "<maritalStatusCode>DIVORCED</maritalStatusCode><phoneNumber>678975459787</phoneNumber>"
			+ "<familyName>Williams</familyName><middleName>Mary</middleName><personIdentifiers><identifierDomain>"
			+ "<identifierDomainName>OpenMRS</identifierDomainName></identifierDomain>"
			+ "<identifier>568749875445698798988873</identifier></personIdentifiers><state>London</state>"
			+ "<email>kathrinwilliams@gmail.com</email></person>";
	
	final private String FHIRtoEMPIXML2 = "<person><deathTime>1982-08-25T07:06:12</deathTime><dateChanged>2017-07-19T08:17:26.188-04:00</dateChanged><country>UK</country><birthOrder>2</birthOrder><gender><genderDescription>Female</genderDescription><genderCode>F</genderCode><genderCd>1</genderCd><genderName>Female</genderName></gender><address2>Penton Rise</address2><city>London</city><prefix>Miss</prefix><address1>Kings Cross</address1><givenName>Kathrin</givenName><postalCode>589632</postalCode><dateOfBirth>1970-09-24</dateOfBirth><mothersMaidenName>Katy</mothersMaidenName><maritalStatusCode>DIVORCED</maritalStatusCode><phoneNumber>678975459787</phoneNumber><familyName>Williams</familyName><middleName>Mary</middleName><personIdentifiers><identifierDomain><identifierDomainName>OpenMRS</identifierDomainName></identifierDomain><identifier>568749875445698798988873</identifier></personIdentifiers><state>London</state><email>kathrinwilliams@gmail.com</email></person>";
	final private String FHIRtoEMPIXML3 = "<person><deathTime>1982-08-25T07:06:12</deathTime><country>UK</country><birthOrder>2</birthOrder><gender><genderDescription>Female</genderDescription><genderCode>F</genderCode><genderCd>1</genderCd><genderName>Female</genderName></gender><address2>Penton Rise</address2><city>London</city><prefix>Miss</prefix><address1>Kings Cross</address1><givenName>Kate</givenName><postalCode>589632</postalCode><dateOfBirth>1970-09-24</dateOfBirth><mothersMaidenName>Kat</mothersMaidenName><maritalStatusCode>DIVORCED</maritalStatusCode><phoneNumber>6789475459787</phoneNumber><familyName>Williams</familyName><middleName>Mary</middleName><personIdentifiers><identifierDomain><identifierDomainName>SSN</identifierDomainName></identifierDomain><identifier>568754549875445698798988873</identifier></personIdentifiers><personId>47</personId><state>London</state><email>katewilliams@gmail.com</email></person>";
	
	@Test
 	public void testPatientSearch() throws Exception {
		OpenEMPIConnector caller = Mockito.mock(OpenEMPIConnector.class);
		PatientFHIR tester = new PatientFHIR(caller);	
		
		JsonNode fhirResource = JsonLoader.fromPath("src/test/resources/ResourceFHIR.json");		
		JSONObject patient = new JSONObject(fhirResource.toString());
		
		
		JSONObject expected = patient;
		expected.remove("meta");
		String death = expected.optString("deceasedDateTime");
		if(expected.has("deceasedDateTime")) {
			expected.remove("deceasedDateTime");
			expected.put("deceasedDateTime", death.substring(0,19) + "Z");
		}
		
	
		String xml = new String(Files.readAllBytes(Paths.get("src/test/resources/patientFHIRResult.xml")),"UTF-8");
		xml = xml.substring(0, xml.length()-1);
		
		Mockito.when(caller.addPerson(FHIRtoEMPIXML)).thenReturn(xml);
		String newRecordID = tester.create(patient);
		JSONObject parameters = new JSONObject(searchParameters);
		
		
		xml = new String(Files.readAllBytes(Paths.get("src/test/resources/patientFHIRSearchResult.xml")),"UTF-8");
		xml = xml.substring(0, xml.length()-1);
		Mockito.when(caller.searchPersonByAttributes(parameters)).thenReturn(xml);
		/* patient model to JSONObject */
		List<Patient> p = tester.search(parameters);
		FhirContext ctx = FhirContext.forDstu2();
		String resourceJson = ctx.newJsonParser().encodeResourceToString(p.get(0));	
		JSONObject obtained_object = new JSONObject(resourceJson);
		
		
		
		
		//JSONObject resultSearch = obtained_object.getJSONArray("entry").getJSONObject(0).getJSONObject("resource");
		obtained_object.remove("id");
		obtained_object.remove("meta");					
//        	OpenEMPIConnector delete = new OpenEMPIConnector();
		System.out.println(newRecordID+ "FOCUS HERE");
//        	delete.removePersonById(newRecordID);
        	Assert.assertEquals("Search operation failed \n",expected.toString(), obtained_object.toString());
	}
	
	@Test
	public void testPatientUpdate() throws ResourceNotFoundException, Exception {
		OpenEMPIConnector caller = Mockito.mock(OpenEMPIConnector.class);
		PatientFHIR tester = new PatientFHIR(caller);	
		
//        	OpenEMPIConnector delete = new OpenEMPIConnector();
		
		JsonNode fhirResource = JsonLoader.fromPath("src/test/resources/ResourceFHIR.json");		
		JSONObject patient = new JSONObject(fhirResource.toString());
		
		
		String xml = new String(Files.readAllBytes(Paths.get("src/test/resources/patientFHIRResult.xml")),"UTF-8");
		xml = xml.substring(0, xml.length()-1);
	
		Mockito.when(caller.addPerson(FHIRtoEMPIXML2)).thenReturn(xml);
  		String newRecordID = tester.create(patient);
  		
		JsonNode fhirResourceUpdate = JsonLoader.fromPath("src/test/resources/updateResourceFhir.json");		
		JSONObject updateCreate = new JSONObject(fhirResourceUpdate.toString());
	
        
      
		Mockito.when(caller.updatePerson(FHIRtoEMPIXML3)).thenReturn("Updated");
		
		String replyExists = tester.update(newRecordID, updateCreate);
//        	delete.removePersonById(newRecordID);
		assertEquals("Update Operation if the record exists failed: ", "Updated", replyExists );		

	}
	
	@Test(expected = FhirSchemeNotMetException.class)
	public void testPatientPatchPathNotExist() throws Exception{
		OpenEMPIConnector caller = Mockito.mock(OpenEMPIConnector.class);
		PatientFHIR tester = new PatientFHIR(caller);	
		
		
		final String jsonPatchTest = "[ { \"op\": \"replace\", \"path\": \"/gender\", \"value\": \"male\" }, {\"op\": \"add\", \"path\": \"/what is this\", \"value\": \"male\" } ]";
		ObjectMapper mapper = new ObjectMapper();
		JsonNode patchNode = mapper.readTree(jsonPatchTest);
		final JsonPatch patch = JsonPatch.fromJson(patchNode);
		
//		OpenEMPIConnector delete = new OpenEMPIConnector();
		JsonNode fhirResource = JsonLoader.fromPath("src/test/resources/ResourceFHIR.json");		
		JSONObject patient = new JSONObject(fhirResource.toString());
		
		String xml = new String(Files.readAllBytes(Paths.get("src/test/resources/patientFHIRResult.xml")),"UTF-8");
		xml = xml.substring(0, xml.length()-1);
	
		Mockito.when(caller.addPerson(FHIRtoEMPIXML2)).thenReturn(xml);
		
  		String newRecordID = tester.create(patient);
  		
  		
  		
		try{
			xml = new String(Files.readAllBytes(Paths.get("src/test/resources/patientReadById.xml")),"UTF-8");
			xml = xml.substring(0, xml.length()-1);
			Mockito.when(caller.readPerson(newRecordID)).thenReturn(xml);
			String xml2 = new String(Files.readAllBytes(Paths.get("src/test/resources/patientPatchedXML.xml")),"UTF-8");
			xml2 = xml2.substring(0, xml2.length()-1);
			Mockito.when(caller.updatePerson(xml2)).thenReturn("Updated");
			tester.patch(newRecordID,patch);
		}finally{
//			delete.removePersonById(newRecordID);
		}
	}
	
	@Test(expected = JsonPatchException.class)
	public void testPatientPatchOperatorsNotExist() throws Exception{
		OpenEMPIConnector caller = Mockito.mock(OpenEMPIConnector.class);
		PatientFHIR tester = new PatientFHIR(caller);	
		final String jsonPatchTest = "[ { \"op\": \"replace\", \"path\": \"/gende\", \"value\": \"male\" } ]";
		ObjectMapper mapper = new ObjectMapper();
		JsonNode patchNode = mapper.readTree(jsonPatchTest);
		final JsonPatch patch = JsonPatch.fromJson(patchNode);
//		OpenEMPIConnector delete = new OpenEMPIConnector();
		JsonNode fhirResource = JsonLoader.fromPath("src/test/resources/ResourceFHIR.json");		
		JSONObject patient = new JSONObject(fhirResource.toString());
		
		String xml = new String(Files.readAllBytes(Paths.get("src/test/resources/patientFHIRResult.xml")),"UTF-8");
		xml = xml.substring(0, xml.length()-1);
		Mockito.when(caller.addPerson(FHIRtoEMPIXML2)).thenReturn(xml);
		
  		String newRecordID = tester.create(patient);
		try{
			xml = new String(Files.readAllBytes(Paths.get("src/test/resources/patientReadById.xml")),"UTF-8");
			xml = xml.substring(0, xml.length()-1);
			Mockito.when(caller.readPerson(newRecordID)).thenReturn(xml);
			String xml2 = new String(Files.readAllBytes(Paths.get("src/test/resources/patientPatchedXML.xml")),"UTF-8");
			xml2 = xml2.substring(0, xml2.length()-1);
			Mockito.when(caller.updatePerson(xml2)).thenReturn("Updated");
			tester.patch(newRecordID,patch);
		}finally{
//		       delete.removePersonById(newRecordID);
		}
	}
	
	@Test
	public void testPatientPatchRecord() throws Exception{
		OpenEMPIConnector caller = Mockito.mock(OpenEMPIConnector.class);
		PatientFHIR tester = new PatientFHIR(caller);	
		final String jsonPatchTest = "[ { \"op\": \"replace\", \"path\": \"/gender\", \"value\": \"male\" } ]";
		ObjectMapper mapper = new ObjectMapper();
		JsonNode patchNode = mapper.readTree(jsonPatchTest);
		final JsonPatch patch = JsonPatch.fromJson(patchNode);
//		OpenEMPIConnector delete = new OpenEMPIConnector();
		JsonNode fhirResource = JsonLoader.fromPath("src/test/resources/ResourceFHIR.json");		
		JSONObject patient = new JSONObject(fhirResource.toString());
		
		String xml = new String(Files.readAllBytes(Paths.get("src/test/resources/patientFHIRResult.xml")),"UTF-8");
		xml = xml.substring(0, xml.length()-1);
		
		Mockito.when(caller.addPerson(FHIRtoEMPIXML2)).thenReturn(xml);
  		String newRecordID = tester.create(patient);
		try{
			xml = new String(Files.readAllBytes(Paths.get("src/test/resources/patientReadById.xml")),"UTF-8");
			xml = xml.substring(0, xml.length()-1);
			Mockito.when(caller.readPerson(newRecordID)).thenReturn(xml);
			
			String xml2 = new String(Files.readAllBytes(Paths.get("src/test/resources/patientPatchedXML.xml")),"UTF-8");
			xml2 = xml2.substring(0, xml2.length()-1);
			Mockito.when(caller.updatePerson(xml2)).thenReturn("Updated");
			
			assertEquals(tester.patch(newRecordID,patch), "Updated");
		}finally{
//		      	delete.removePersonById(newRecordID);
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
