/**
 * 
 */
package fhirconverter.converter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Assert;
import org.junit.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

/**
 * @author Shruti Sinha
 *
 */

public class PatientEvaluationTest {

	private static final Logger LOGGER = LogManager.getLogger(PatientEvaluationTest.class.getName());

	public static String baseURL = "http://localhost:8090/fhir";
	public static String baseURLHAPI = "http://fhirtest.uhn.ca/baseDstu3";

	@Test
	public void checkDataIntgrity() throws Exception {

		String createRequest = new String(Files.readAllBytes(Paths.get("resource/PatientFHIRRequest.xml")), "UTF-8");
		String patientIdHAPI = patientCreateHAPI(createRequest);
		String patientIdNHS = this.patientCreateNHS(createRequest);
		String readHAPIPatient = "";
		String readNHSPatient = "";

		if(!("").equals(patientIdHAPI)){
			readHAPIPatient = this.patientReadHAPI(patientIdHAPI);
		}else{
			readHAPIPatient = new String(Files.readAllBytes(Paths.get("resource/PatientFHIRRequest.xml")), "UTF-8");
		}

		if(!("").equals(patientIdNHS)){
			readNHSPatient = this.patientReadHAPI(patientIdHAPI);
		}else{
			readNHSPatient = new String(Files.readAllBytes(Paths.get("resource/c4h.xml")), "UTF-8");
		}

		/* To calculate Growth Chart loss*/
		float loss = this.calculateLossGCA(readHAPIPatient, readNHSPatient);
		Assert.assertTrue(loss<20);
		LOGGER.info("Data Loss is less than 20%");

	}

	public String patientCreateHAPI(String createRequest) throws Exception{

		String patientId = "";
		try{
			HttpResponse<String> response = Unirest.post(baseURLHAPI +"/Patient")
					.header("Content-Type", "application/xml")
					.body(createRequest)
					.asString(); 
			if(response.getStatus() == 201){
				patientId = response.getHeaders().getFirst("Location").replace("http://fhirtest.uhn.ca/baseDstu3/Patient/", "");
				patientId = patientId.substring(0, patientId.indexOf("/"));
			}
			LOGGER.info("Patient Id created on HAPI FHIR server: " + patientId);
			return patientId;
		}catch(Exception e){
			LOGGER.info("Exception: " + e);
			LOGGER.info("Error in creating Patient on HAPI FHIR server, so will refer to a sample response");
			return patientId;
		} 
	}

	public String patientCreateNHS(String createRequest) throws Exception{

		String patientId = "";
		try{
			HttpResponse<String> response = Unirest.post(baseURL+"/Patient")
					.header("Content-Type", "application/xml")
					.body(createRequest)
					.asString(); 
			if(response.getStatus() == 201){
				patientId = response.getHeaders().getFirst("Location").replace("http://localhost:8090/fhir/Patient/", "");
				patientId = patientId.substring(0, patientId.indexOf("/"));
			}
			LOGGER.info("Patient Id created on NHS server: " + patientId);
			return patientId;
		}catch(Exception e){
			LOGGER.info("Exception: " + e);
			LOGGER.info("Error in creating Patient on NHS server, so will refer to a sample response");
			return patientId;
		} 
	}

	public String patientRead(String patientId) throws Exception{

		try{
			HttpResponse<String> response = Unirest.get(baseURL+"/Patient/" + patientId)
					.asString();
			if(response.getStatus() == 200)
				return response.getBody().toString();
			else
				return "";
		}catch(Exception e){
			LOGGER.info("Exception: " + e);
			LOGGER.info("Error in reading Patient on NHS server, so will refer to a sample response");
			return "";
		} 
	}

	public String patientReadHAPI(String patientId) throws Exception{

		try{
			HttpResponse<String> response = Unirest.get(baseURLHAPI +"/Patient/" + patientId)
					.asString();
			if(response.getStatus() == 200)
				return response.getBody().toString();
			else
				return "";
		}catch(Exception e){
			LOGGER.info("Exception: " + e);
			LOGGER.info("Error in reading Patient on HAPI FHIR server, so will refer to a sample response");
			return "";
		} 
	}	

	public float calculateLossGCA(String hapiFHIR, String code4Health) throws Exception{

		int hapiFields = 0;
		int nHSFields = 0;
		JSONObject hapiJson = this.prepareJSON(hapiFHIR);
		JSONObject nHSJson = this.prepareJSON(code4Health);

		Map<String, String> hapiMap = this.extractJsonFields(hapiJson);
		hapiFields = hapiMap.size();

		if(nHSJson.has("name") && !nHSJson.optJSONObject("name").equals("")){
			JSONObject nameJson = nHSJson.optJSONObject("name");
			if(nameJson.has("given") && !nameJson.optString("given").equals("") && nameJson.optJSONObject("given").getString("value").equalsIgnoreCase(hapiMap.get("given"))){
				nHSFields++;
			}
			if(nameJson.has("family") && !nameJson.optString("family").equals("")&& nameJson.optJSONObject("family").getString("value").equals(hapiMap.get("family"))){
				nHSFields++;
			}
		}
		if(nHSJson.has("gender") && !nHSJson.optJSONObject("gender").equals("") && nHSJson.optJSONObject("gender").getString("value").equalsIgnoreCase(hapiMap.get("gender"))){
			nHSFields++;
		}
		if(nHSJson.has("birthDate") && !nHSJson.optJSONObject("birthDate").equals("") && nHSJson.optJSONObject("birthDate").getString("value").equalsIgnoreCase(hapiMap.get("birthDate"))){
			nHSFields++;
		}

		float absent = hapiFields - nHSFields;
		float loss = absent/hapiFields * 100;

		LOGGER.info("*** No of fields in HAPI FHIR server response that is required by GCA: " + hapiFields + " ***");
		LOGGER.info("*** No of fields in NHS server response that is required by GCA: " + nHSFields + " ***");
		LOGGER.info("*** Data Loss: " + loss + " ***");
		return loss;

	}

	public Map<String, String> extractJsonFields(JSONObject json) throws Exception {

		Map<String, String> hapiMap = new HashMap<>();
		if(json.has("name") && !json.optJSONArray("name").equals("")){
			//JSONObject nameJson = json.optJSONObject("name");
			JSONArray nameJsonArray = json.optJSONArray("name");
			for(int i = 0; i < nameJsonArray.length(); i++) {
				JSONObject nameJson = nameJsonArray.getJSONObject(i);
				if(nameJson.has("given") && !nameJson.optString("given").equals("")){
					String given = nameJson.optString("given").replaceAll("\"", "");
					given = given.replace("[", "");
					given = given.replace("]", "");
					hapiMap.put("given", given);
				}
				if(nameJson.has("family") && !nameJson.optString("family").equals("")){
					hapiMap.put("family", nameJson.optString("family"));
				}
			}
		}
		if(json.has("gender") && !("").equals(json.optString("gender"))){
			hapiMap.put("gender", json.optString("gender"));
		}
		if(json.has("birthDate") && !("").equals(json.optJSONObject("birthDate"))){
			hapiMap.put("birthDate", json.optString("birthDate"));
		}
		return hapiMap;
	}


	public JSONObject prepareJSON(String response) throws Exception {

		JSONObject json = null;
		if(response.substring(0, 1).equals("<")){
			json = XML.toJSONObject(response);

			JSONObject patient = json.optJSONObject("Patient");
			if(patient.has("meta")){
				patient.remove("meta");
			}
			if(patient.has("id")){
				patient.remove("id");
			}
			if(patient.has("text")){
				patient.remove("text");
			}
			return patient;
		}else{
			json = new JSONObject(response);
			json.remove("Patient");
			json.remove("meta");
			json.remove("id");
			json.remove("text");
			return json;
		}
	}
}