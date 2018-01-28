package fhirconverter.converter; 
import ca.uhn.fhir.model.dstu2.resource.Observation;
import fhirconverter.utilities.PatientHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

//modified at 18/01/23 by Chenghui, created private variables patientHelper, openEHRconnector and openEHRconvertor to make the methods testable
public class ObservationFHIR{
	private PatientHelper patientHelper;
	private OpenEHRConnector openEHRconnector;
	private OpenEHRConvertor openEHRconvertor;
	private Logger LOGGER = LogManager.getLogger(ObservationFHIR.class);
 
	public ObservationFHIR(){
		initializeOpenEHRConnector();
		this.patientHelper = new PatientHelper();
		this.openEHRconvertor = new OpenEHRConvertor();
	}
	
	public ObservationFHIR(PatientHelper patientHelper, OpenEHRConnector openEHRconnector, OpenEHRConvertor openEHRconvertor) throws Exception{
		this.openEHRconnector = openEHRconnector;
		this.patientHelper = patientHelper;
		this.openEHRconvertor = openEHRconvertor;
	}
	
	private void initializeOpenEHRConnector(){
		HashMap<String,String> connectionCreds = Utils.getDataBase();
		String domainName = connectionCreds.get("database");
		try {
			this.openEHRconnector = new OpenEHRConnector(domainName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Observation> search(String patientId, ArrayList<String> searchParams) throws Exception{
		String nhsNumber = patientHelper.retrieveNHSbyId(patientId);	//rewrite
		LOGGER.debug("nhsNumber" + nhsNumber);

//		Chenghui: not sure will removing this line will cause thread failure TODO: further verification
//		OpenEHRConnector openEHRconnector = new OpenEHRConnector(domainName); // Future developers, Note this line of code is placed here to be thread safe.		
		
		org.json.simple.JSONObject aqlPaths = Utils.readJsonFile("aql_path.json");
		JSONObject aqlJSONObj =  new JSONObject(aqlPaths.toString());
		LOGGER.debug(aqlJSONObj.toString(3));
		String ehrNumber = openEHRconnector.getEHRIdByNhsNumber(nhsNumber);		//rewrite
		
		JSONObject aqlFilteredObj = filterPathsByParams(aqlJSONObj, searchParams);
		String aqlQuery = constructDynamicAQLquery(ehrNumber, aqlFilteredObj, searchParams);
		JSONObject observationObj = openEHRconnector.getObservations(aqlQuery);	//rewrite
		
		LOGGER.debug(observationObj.toString(3));
		LOGGER.debug("observationObj " + observationObj.toString(3));
		
		
        observationObj.put("patientId", patientId);
        LOGGER.debug(this.openEHRconvertor.jsonToObservation(observationObj));
		return this.openEHRconvertor.jsonToObservation(observationObj);
	}

	private String constructDynamicAQLquery(String ehrNumber, JSONObject aqlFilteredObj, ArrayList<String> searchParams){
		String selectString = "select";
		String fromString = " from EHR [ehr_id/value='"+ehrNumber+"'] contains COMPOSITION c" ;//3
		String containmentString = " contains (";
		Iterator<String> iter = aqlFilteredObj.keys();
		while (iter.hasNext()) {
    			String key = iter.next();
    			try {
				JSONObject pathObj = aqlFilteredObj.getJSONObject(key).getJSONObject("path");
				String archetypeIdentifier = aqlFilteredObj.getJSONObject(key).getString("text");
				String archetypeString = aqlFilteredObj.getJSONObject(key).getString("archetype");
				selectString += constructSelectStatement(key,pathObj, archetypeIdentifier);
				containmentString +=  constructContainmentStatement(archetypeIdentifier, archetypeString);
    			} catch (Exception e) {
        			e.printStackTrace();
   				}
		}
		selectString = selectString.substring(0, selectString.length() - 1);
		containmentString = containmentString.substring(0, containmentString.length() - 2);
		containmentString += ")";
		String constructedAQLString = selectString + fromString + containmentString;
		LOGGER.debug("Select statement "+ constructedAQLString); 
		return constructedAQLString;	
	}

	private JSONObject filterPathsByParams(JSONObject aqlPaths, ArrayList<String> searchParams){
		JSONObject filteredPath = new JSONObject();
		for(String searchParam : searchParams){
			if(aqlPaths.has(searchParam)){
				filteredPath.put(searchParam, aqlPaths.getJSONObject(searchParam));
			}
		}
		LOGGER.debug("FILTERED"+filteredPath.toString(3));
		return filteredPath;
	}

	private String constructSelectStatement(String aqlFilteredKey, JSONObject pathObj, String archetypeIdentifier){
		Set keys = pathObj.keySet();
		String selectString = "";
   		Iterator a = keys.iterator();
		String aqlReplacedKey = aqlFilteredKey.replaceAll("-","_");
    	while(a.hasNext()) {
			String key = (String)a.next();
        	String value = (String)pathObj.get(key);
			selectString += " "+archetypeIdentifier+"/"+value+ " as " + "LONIC_"+aqlReplacedKey+"_"+key+",";
		}
		return selectString;		
	}

	private String constructContainmentStatement(String archetypeIdentifier, String archetypeString){
		String containmentStatementString = " OBSERVATION "+archetypeIdentifier+"["+archetypeString+"] or";
		return containmentStatementString;
	}
}
