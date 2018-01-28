package fhirconverter.converter;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import fhirconverter.exceptions.IdNotObtainedException;
import org.json.JSONObject;

import java.util.HashMap;

public class OpenEHRConnector{
	private String baseURL;
	private String username;
	private String password;
	private String sessionCode;
 
	OpenEHRConnector(String domainName) throws Exception{
		HashMap<String,String> connectionCreds = Utils.getProperties(domainName);
		baseURL = connectionCreds.get("baseURL");
		username = connectionCreds.get("username");
		password =  connectionCreds.get("password");
		sessionCode = getSessionCode();
	}
	private String getSessionCode() throws Exception{
		if(sessionCode == null){
			try{
				HttpResponse<String> response = Unirest.post(baseURL+"/rest/v1/session?"+"username="+username+"&password="+password)
  								.asString(); 
				JSONObject responseObj = new JSONObject(response.getBody());
				if(responseObj.has("sessionId")){
					return responseObj.getString("sessionId");
			 	}else{
					throw new IdNotObtainedException("Session ID not Created!");
				}		
			}catch(UnirestException e){
				e.printStackTrace();
			}
		}	
		return null;
	}
	public String getEHRIdByNhsNumber(String nhsNumber) throws Exception{
		try{
			HttpResponse<String> response = Unirest.get(baseURL+"/rest/v1/ehr/?subjectId="+nhsNumber+"&subjectNamespace=uk.nhs.nhs_number")
 							 .header("content-type", "application/json")
  							 .header("Ehr-Session", sessionCode)
  							 .asString();		
			if(response.getStatus() == 200){
		        	JSONObject responseObj = new JSONObject(response.getBody());
				if(responseObj.has("ehrId")){
					return responseObj.getString("ehrId");
				}
				return null;
			}
			else{
				throw new IdNotObtainedException("ehrID not found!");
			}
		}catch(UnirestException e){
			e.printStackTrace();	
			return null;
		}
		
	}
	public JSONObject getGrowthChartObservations(String nhsNumber) throws Exception{
		String ehrID = getEHRIdByNhsNumber(nhsNumber);
		String aqlString = "select%20b_a%2Fdata%5Bat0002%5D%2Forigin%2Fvalue%20as%20Weight_date%2C%20b_a%2Fdata%5Bat0002%5D%2Fevents%5Bat0003%5D%2Fdata%5Bat0001%5D%2Fitems%5Bat0004%5D%2Fvalue%2Fmagnitude%20as%20Weight_magnitude%2C%20b_a%2Fdata%5Bat0002%5D%2Fevents%5Bat0003%5D%2Fdata%5Bat0001%5D%2Fitems%5Bat0004%5D%2Fvalue%2Funits%20as%20Weight_units%2C%20b_b%2Fdata%5Bat0001%5D%2Forigin%2Fvalue%20as%20Height_Length_date%2C%20b_b%2Fdata%5Bat0001%5D%2Fevents%5Bat0002%5D%2Fdata%5Bat0003%5D%2Fitems%5Bat0004%5D%2Fvalue%2Fmagnitude%20as%20Height_Length_magnitude%2C%20b_b%2Fdata%5Bat0001%5D%2Fevents%5Bat0002%5D%2Fdata%5Bat0003%5D%2Fitems%5Bat0004%5D%2Fvalue%2Funits%20as%20Height_Length_units%2C%20b_c%2Fdata%5Bat0001%5D%2Forigin%2Fvalue%20as%20Head_circumference_date%2C%20b_c%2Fdata%5Bat0001%5D%2Fevents%5Bat0010%5D%2Fdata%5Bat0003%5D%2Fitems%5Bat0004%5D%2Fvalue%2Fmagnitude%20as%20Head_circumference_magnitude%2C%20b_c%2Fdata%5Bat0001%5D%2Fevents%5Bat0010%5D%2Fdata%5Bat0003%5D%2Fitems%5Bat0004%5D%2Fvalue%2Funits%20as%20Head_circumference_units%2C%20b_d%2Fdata%5Bat0001%5D%2Forigin%2Fvalue%20as%20Body_Mass_index_date%2C%20b_d%2Fdata%5Bat0001%5D%2Fevents%5Bat0002%5D%2Fdata%5Bat0003%5D%2Fitems%5Bat0004%5D%2Fvalue%2Fmagnitude%20as%20Body_Mass_Index_magnitude%2C%20b_d%2Fdata%5Bat0001%5D%2Fevents%5Bat0002%5D%2Fdata%5Bat0003%5D%2Fitems%5Bat0004%5D%2Fvalue%2Funits%20as%20Body_Mass_Index_units%2C%20b_f%2Fdata%5Bat0001%5D%2Forigin%2Fvalue%20as%20Skeletal_age_date%2C%20b_f%2Fdata%5Bat0001%5D%2Fevents%5Bat0002%5D%2Fdata%5Bat0003%5D%2Fitems%5Bat0005%5D%2Fvalue%2Fvalue%20as%20Skeletal_age%20from%20EHR%20%5Behr_id%2Fvalue%3D'" +ehrID +"'%5D%20contains%20COMPOSITION%20a%5BopenEHR-EHR-COMPOSITION.report.v1%5D%20contains%20(%20OBSERVATION%20b_a%5BopenEHR-EHR-OBSERVATION.body_weight.v1%5D%20or%20OBSERVATION%20b_b%5BopenEHR-EHR-OBSERVATION.height.v1%5D%20or%20OBSERVATION%20b_c%5BopenEHR-EHR-OBSERVATION.head_circumference.v0%5D%20or%20OBSERVATION%20b_d%5BopenEHR-EHR-OBSERVATION.body_mass_index.v1%5D%20or%20OBSERVATION%20b_f%5BopenEHR-EHR-OBSERVATION.skeletal_age.v0%5D)";
		try{
				HttpResponse<String> response = Unirest.get(baseURL+"/rest/v1/query?aql="+aqlString)
 		    				    		.header("content-type", "application/json")
                    				    		.header("Ehr-Session", sessionCode)
                    				    		.asString();
				if(response.getStatus() == 200){
					JSONObject responseObj = new JSONObject(response.getBody());
					return responseObj;
				}	
			}catch(UnirestException e){
				e.printStackTrace();
				return null;
			}		
		return null;
	}
	public JSONObject getObservations(String aqlString) throws Exception{
		try{
				HttpResponse<String> response = Unirest.post(baseURL+"/rest/v1/query")
 		    				    		.header("content-type", "application/json")
                    				    		.header("Ehr-Session", sessionCode)
								.body("{\"aql\": \""+aqlString+ "\"}")
                    				    		.asString();
				if(response.getStatus() == 200){
					JSONObject responseObj = new JSONObject(response.getBody());
					return responseObj;
				}	
			}catch(UnirestException e){
				e.printStackTrace();
				return null;
			}		
		return null;
	}
	public void deleteSessionKey() throws Exception{
		try{
			HttpResponse<String> response = Unirest.delete(baseURL+"/rest/v1/session")
  							.header("content-type", "application/json")
  							.header("Ehr-Session", sessionCode)
  							.asString();
		}catch(UnirestException e){
			e.printStackTrace();
		}

	}


}
