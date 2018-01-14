package fhirconverter.utilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import fhirconverter.converter.OpenEMPIConnector;
import fhirconverter.exceptions.IdNotObtainedException;
import fhirconverter.exceptions.OpenEMPISchemeNotMetException;

public class PatientHelper {
	private static final Logger logger = LogManager.getLogger(PatientHelper.class.getName());

	
	public String retrieveNHSbyId(String id) throws Exception {
		String nhsIdentifier = "";
		
		OpenEMPIConnector caller = new OpenEMPIConnector();
		String patientXML = caller.readPerson(id);
		
		JSONObject xmlJSONObj = XML.toJSONObject(patientXML);
		if(xmlJSONObj.has("person")) {
			JSONObject patient = xmlJSONObj.getJSONObject("person");
			if(patient.has("personIdentifiers")) {
				logger.info("\n\n PATIENT OBJECT helper \n " + patient.toString(3));
				
				JSONArray multipleIdentifiers = patient.optJSONArray("personIdentifiers"); 
				
				if(multipleIdentifiers==null) {
					JSONObject singleIdentifier = patient.getJSONObject("personIdentifiers");
					nhsIdentifier = getIdentifierIfNHS(singleIdentifier);
				}
				else {
					for(int i=0; i<multipleIdentifiers.length(); i++) {
						JSONObject singleIdentifier = multipleIdentifiers.getJSONObject(i);
						nhsIdentifier = getIdentifierIfNHS(singleIdentifier);
						if(!"".equals(nhsIdentifier))
							break;
						
					}
				}
			}			
			if("".equals(nhsIdentifier)) {
				throw new IdNotObtainedException("There is no NHS identifier in OpenEMPI for this patient");
			}	
			
		}else {
			throw new OpenEMPISchemeNotMetException("The Parameters does not confine to OpenEMPIScheme");
		}
		return nhsIdentifier;
	}
	
	private String getIdentifierIfNHS(JSONObject identifierObject) {
		String identifier = "";
		if(identifierObject.has("identifierDomain")) {
			String domainName = identifierObject.getJSONObject("identifierDomain").optString("identifierDomainName");
			if(domainName.toLowerCase().contains("nhs")) {
				identifier = identifierObject.optString("identifier");
			}			
		}		
		return identifier;		
	}
	
}
