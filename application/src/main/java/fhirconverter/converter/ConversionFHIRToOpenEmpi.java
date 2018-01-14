/**
 * ConversionFHIR_to_OpenEMPI
 * 
 * v2.0
 * 
 * Date: 13-7-2017
 * 
 * Copyrights: Koon Wei Teo, Evanthia Tingiri, Shruti Sinha
 * 
 * Description: This class contains the necessary functions to convert FHIR to OpenEMPI.
 * 				It is called by PatientFHIR in the case of the following requests:
 * 				Create, Update and Patch
 * 
 */

package fhirconverter.converter;

import org.json.JSONArray;
import org.json.JSONObject;

public class ConversionFHIRToOpenEmpi {
	
	
	protected JSONObject conversionToOpenEMPI(JSONObject patient) {
		JSONObject content = new JSONObject();
		
		/* SET THE FULL NAME FROM FHIR TO OPENEMPI */	
		content = setFullNames(patient, content);
		/* SET THE ADDRESS FROM FHIR TO OPENEMPI */	
		content = setAddresses(patient, content);
		
		
		/* SET THE MARITAL STATUS FROM FHIR TO OPENEMPI */
		content = setMaritalStatus(patient, content);
		/* SET PHONE NUMBER AND EMAIL FROM FHIR TO OPENEMPI */		
		content = setTelecom(patient, content);

		/* SET **GENDER** FROM FHIR TO OPENEMPI */	
		JSONObject genderDetails = setGender(patient);		
		content.put("gender", genderDetails);	
		
		
		/* SET BIRTH ORDER FROM FHIR TO OPENEMPI */		
		content = checkExistsAndPut(patient.has("multipleBirthInteger"), "multipleBirthInteger", patient,content, "birthOrder", patient.optString("multipleBirthInteger"));

		/* SET DEATH TIME FROM FHIR TO OPENEMPI */
		content = setDeathTime(patient, content);
		
		/* SET DATE OF BIRTH FROM FHIR TO OPENEMPI */ 
		content = checkExistsAndPut(patient.has("birthDate"), "birthDate", patient,content, "dateOfBirth", patient.optString("birthDate"));

		/* SET DATE OF CHANGE FROM FHIR TO OPENEMPI */
		content = setMetaData(patient, content);

		/* SET IDENTIFIER FROM FHIR TO OPENEMPI */ //need fixing
		content = setPersonIdentifier( patient, content);
				
		return content;
	}
	

	protected JSONObject setGender(JSONObject patient) {
		JSONObject genderDetails = new JSONObject();
		if(patient.has("gender")){
			if("female".equals(patient.getString("gender"))){
				genderDetails.put("genderCd","1");
				genderDetails.put("genderCode","F");
				genderDetails.put("genderDescription","Female");
				genderDetails.put("genderName","Female");
			}
			else if("male".equals(patient.getString("gender"))){
				genderDetails.put("genderCd","2");
				genderDetails.put("genderCode","M");
				genderDetails.put("genderDescription","Male");
				genderDetails.put("genderName","Male");
			}
			else if("other".equals(patient.getString("gender"))){
				genderDetails.put("genderCd","3");
				genderDetails.put("genderCode","O");
				genderDetails.put("genderDescription","Other");
				genderDetails.put("genderName","Other");
			}
			else if("unknown".equals(patient.getString("gender"))){
				genderDetails.put("genderCd","4");
				genderDetails.put("genderCode","U");
				genderDetails.put("genderDescription","Unknown");
				genderDetails.put("genderName","Unknown");
			}

		}
		return genderDetails;
	}
	
	protected JSONObject createName(JSONObject content,JSONObject details) {		
			JSONObject temp = content;
			/* - Family Name - */
			temp = checkExistLengthAndPut("family", details, temp, "familyName", 0);

			/* Given Name: JSONArray because it contails First & Middle Name */
			temp = checkExistLengthAndPut("given", details, temp, "givenName", 0);
			temp = checkExistLengthAndPut("given", details, temp, "middleName", 1);

			/* Prefix & Prefix
			 * It is a JSONArray - but OpenEMPI accepts only one
			 * We get the first one
			 */
			temp = checkExistLengthAndPut("prefix", details, temp, "prefix", 0);

		
			temp = checkExistLengthAndPut("suffix", details, temp, "suffix", 0);
			
			return temp;
		}
		
		protected JSONObject createAddress(JSONObject content,JSONObject address) {
			JSONObject temp = content;
			/* line[0]=Address 1 & Line[1] = Address 2 (if they exist) */
			temp = checkExistLengthAndPut("line", address, temp, "address1", 0);
			temp = checkExistLengthAndPut("line", address, temp, "address2", 1);

			/* City */
			temp = checkExistsAndPut(address.has("city"),"city", address, temp, "city", address.optString("city"));
			
			/* Country */ 
			temp = checkExistsAndPut(address.has("country"),"country", address,temp, "country", address.optString("country"));
			
			/* State */
			temp = checkExistsAndPut(address.has("state"),"state", address, temp,"state", address.optString("state"));
			
			/* Postal Code */
			temp = checkExistsAndPut(address.has("postalCode"),"postalCode", address, temp,"postalCode", address.optString("postalCode"));
			return content;
		}

	protected JSONObject setFullNames(JSONObject patient, JSONObject content) {
		JSONObject temp = content;

		if(patient.has("name")) {
			
			/* FHIR has multiple names */
			JSONArray Namesarray = patient.optJSONArray("name");
			
			if(Namesarray!=null) {
				
				/* OpenEMPI takes only official and maiden name */
				boolean officialFound = false; // it found official name
			
				for(int i=0; i<Namesarray.length(); i++) {
					JSONObject details = Namesarray.getJSONObject(i);
					
					/* Define maiden name if it exists */
					if((details.has("use"))&&"maiden".equals(details.optString("use"))&&(details.getJSONArray("family").length() > 0)) {
						temp.put("mothersMaidenName", details.getJSONArray("family").getString(0));
					}
					
					if((details.has("use"))&&("usual".equals(details.optString("use")))) {
						officialFound = true;
						temp = createName(temp,details);
						continue;
					}				
				}
				
				//didn't find official - gets the first one
				if(!officialFound) {
					JSONObject details = Namesarray.getJSONObject(0);
					temp = createName(temp,details);
				}
			}			
		}
		return temp;
	}
	protected JSONObject checkExistsAndPut(boolean condition,  String searchField,JSONObject ConditionObject,JSONObject receiver, String key, Object value) {
		JSONObject temp = receiver;

		if(condition) {
			temp.put(key, value);
		}
		return temp;
	}
	
	protected JSONObject checkExistLengthAndPut(String searchField,JSONObject ConditionObject, JSONObject receiver, String key, int threshold) {
		JSONObject temp = receiver;

		if(ConditionObject.has(searchField)&&((ConditionObject.getJSONArray(searchField).length() > threshold))) {
				temp.put(key, new String(ConditionObject.getJSONArray(searchField).getString(threshold)));
		}				
		return temp;
	}
	protected JSONObject setMaritalStatus(JSONObject patient, JSONObject content) {
		JSONObject temp = content;

		if(patient.has("maritalStatus")) {
			JSONObject status = patient.getJSONObject("maritalStatus");
			if(status.has("coding")) {
				JSONObject code = status.getJSONArray("coding").getJSONObject(0);
				System.out.println(code.toString());
				if(code.has("code")){
					String codeName = code.optString("code");
					if("M".equals(codeName)){
						codeName = "MARRIED";
					}else if("D".equals(codeName)){
						codeName = "DIVORCED";
					}else if("I".equals(codeName)){
						codeName = "INTERLOCUTORY";
					}else if("L".equals(codeName)){
						codeName = "LEGALLY SEPARATED";
					}else if("P".equals(codeName)){
						codeName = "POLYGAMOUS";
					}else if("S".equals(codeName)){
						codeName = "NEVER MARRIED";
					}else if("T".equals(codeName)){
						codeName = "DOMESTIC PARTNER";
					}else if("W".equals(codeName)){
						codeName = "WIDOWED";
					}else if("A".equals(codeName)){
						codeName = "ANNULLED";
					}else{
						codeName = "UNKNOWN";
					}
					System.out.println(codeName + "HI THERE");
					temp.put("maritalStatusCode", codeName);
				}
			}
		}
		return temp;
	}
	
	protected JSONObject setDeathTime(JSONObject patient, JSONObject content) {
		JSONObject temp = content;
		if(patient.has("deceasedDateTime")) {
			String date = patient.optString("deceasedDateTime");
			date = date.substring(0, 19);
			temp.put("deathTime", date);
		}		
		return temp;
	}
	
	protected JSONObject setAddresses(JSONObject patient, JSONObject content) {
		JSONObject temp = content;

		if(patient.has("address")) {
			
			/* Multiple addresses in FHIR but OpenEMPI takes only one */
			JSONArray addresses = patient.getJSONArray("address");
			if(addresses.length()>0) {
				JSONObject address = addresses.getJSONObject(0);
				temp = createAddress(temp, address);			
			}									
		}
		return temp;
	}
	protected JSONObject setTelecom(JSONObject patient, JSONObject content) {
		JSONObject temp = content;

		if(patient.has("telecom")) {
			
			/* Telecom is an array of all the contact details of the patient*/
			JSONArray telecom = patient.getJSONArray("telecom");
			for(int i=0; i<telecom.length(); i++) {
				JSONObject system = telecom.getJSONObject(i);
				
				/* Phone */
				temp = checkExistsAndPut(((system.has("system"))&&("phone".equals(system.getString("system")))),
						"system", system,temp, "phoneNumber", system.optString("value"));

				temp = checkExistsAndPut(((system.has("system"))&&("email".equals(system.getString("system")))),
						"system", system,temp, "email", system.optString("value"));

			}			
		}
		
		
		
		return temp;
	}
	
	protected JSONObject setMetaData(JSONObject patient, JSONObject content) {
		JSONObject temp = content;
		if(patient.has("meta")&&(patient.getJSONObject("meta").has("lastUpdated"))) {
				temp.put("dateChanged", patient.getJSONObject("meta").optString("lastUpdated"));
		}
		return temp;
	}
	
	protected JSONObject setPersonIdentifier( JSONObject patient,JSONObject content) {
		JSONObject temp = content;
		if(patient.has("identifier")) {
			JSONArray personIdentifier = new JSONArray();
	
			/**
			 *
			 * TODO: Get all the identifiers
			 * 
			 */
			JSONArray receivedIdentifiers = patient.getJSONArray("identifier");
			
			if(receivedIdentifiers.length()>0) {
				for(int j=0; j<receivedIdentifiers.length(); j++) {
					JSONObject identifier = new JSONObject();
					JSONObject systemID = receivedIdentifiers.getJSONObject(j);
					identifier.put("identifier", systemID.optString("value"));
					JSONObject identifierDomain = new JSONObject();
					identifierDomain.put("identifierDomainName", systemID.optString("system"));	
					identifier.put("identifierDomain", identifierDomain);				
					personIdentifier.put(identifier);
				}
				
			}	
			temp.put("personIdentifiers", personIdentifier);
		}
		return temp;
	}
	
}
