/**
 * 
 */
package fhirconverter.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import ca.uhn.fhir.model.dstu2.composite.CodingDt;
import ca.uhn.fhir.model.dstu2.composite.QuantityDt;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.primitive.DateTimeDt;

import java.util.concurrent.ThreadLocalRandom;
/**
 * @author Shruti Sinha
 *
 */
public class OpenEHRConvertor {

	private static final Logger logger = LogManager.getLogger(OpenEHRConvertor.class.getName());

	/**
	 * This method creates a map for mapping the LONIC code for some observations to their real world meaning
	 * The Observations defined in the codeMap() can be mapped from EHR response to FHIR Observation
	 * 
	 * @return Map<String, String>
	 */
	public static Map<String, String> codeMap() {
		
		Map<String, String> codeMap = new HashMap<>();
		codeMap.put("3141-9", "Weight");
		codeMap.put("8302-2", "Height");
		codeMap.put("39156-5", "BMI");
		codeMap.put("8287-5", "Head circumference");
		codeMap.put("37362-1", "XR Bone age");
		return codeMap;
	}

	/**
	 * This method is the accepts the JSON object from EHR system and returns
	 * and equivalent list of Observation objects
	 * 
	 * @param jsonResult: JSONObject from EHR system
	 * @return List<Observation>
	 * 
	 * @throws Exception
	 */
	public List<Observation> jsonToObservation(JSONObject jsonResult) throws Exception {

		logger.debug("*** Method: jsonToObservation | input JSON from EHR system: ***" + jsonResult.toString());
		List<Observation> observationList = new ArrayList<>();
		String patientId = "";
		JSONObject newJsonResult = this.prepareInputJSON(jsonResult);
		if (newJsonResult.has("patientId")) {
			patientId = newJsonResult.optString("patientId");
		}
		/*** Checks if the JSON body contains resutlSet node or not ***/
		if (newJsonResult.has("resultSet")) {
			/*
			 * resultSet node is an array, so if resultSet node exists, the
			 * values are stored in resultSetJSONArray and iterated to retrieve
			 * each resultSet element to be mapped to Observation FHIR
			 */			
			JSONArray resultSetJSONArray = newJsonResult.optJSONArray("resultSet");			
			logger.debug("*** Method: jsonToObservation | Array size for resultSet in input JSON: " 
			+ resultSetJSONArray.length() + " ***");
			
			for (int i = 0; i < resultSetJSONArray.length(); i++) {
				JSONObject resultSet = resultSetJSONArray.getJSONObject(i);
				observationList.addAll(mapObservation(resultSet, patientId));
			}
		}
		logger.debug("*** Method: jsonToObservation | observationList size: " + observationList.size() + " ***");
		return observationList;
	}

	/**
	 * This method accepts resultSet and patient Id. resultSet is a JSONObject that contain multiple EHR Observations entries. 
	 * Entries that corresponds to a similar Observation has same LONIC code suffix like, 3141-9 representing weight observation.
	 * This method creates a map, where key would be LONIC code and value would be the newly created Observation object(s) from resultSet.
	 * The method then iterates through the resultSet JSONObject & checks if an object corresponding to the LONIC code in the jsonNode is 
	 * already created in the map. If not a new entry for that new Observation is created in the map, else the exiting map for that LONIC 
	 * code is updated with the jsonNode value (only if the value corresponding to the selected jsonNode in the resultSet is not null) 
	 * 
	 * @param resultSet: JSONObject
	 * @param patientId: String
	 * @return List<Observation>
	 * 
	 * @throws Exception
	 */
	public List<Observation> mapObservation(JSONObject resultSet, String patientId) throws Exception {

		Map<String, String> codeMap = codeMap();
		JSONObject newResultSet = this.prepareResultSet(resultSet);
		Iterator<?> jsonKeys = newResultSet.keys();
		Map<String, Observation> obsMap = new HashMap<>();
		while (jsonKeys.hasNext()) {

			String jsonNode = jsonKeys.next().toString();
			logger.debug("*** Method: mapObservation | jsonNode = " + jsonNode + " ***");
			String key = jsonNode.substring(0, jsonNode.lastIndexOf("-"));
			
			/* Checks if the Observation key is already present in the map, if yes then set the parameter in the
			 * corresponding Observation value for the key */
			if (obsMap.containsKey(key)) {
				this.setParameters(obsMap, newResultSet, jsonNode, key);
				
			} else if(!newResultSet.optString(jsonNode).equals("")){
				Observation observation = new Observation();
				logger.debug("*** Method: mapObservation | Adding new Observation for key  = " + key + " ***");
				observation.getCode().addCoding(new CodingDt("http://loinc.org", key));
				observation.getCode().setText(codeMap.get(key));
				QuantityDt quantity = new QuantityDt();
				quantity.setValue(0.0);
				quantity.setUnit("");
				quantity.setCode("");
				quantity.setSystem("http://unitsofmeasure.org");
				observation.setValue(quantity);
				observation.getSubject().setReference(patientId);
                observation.setId(String.valueOf(ThreadLocalRandom.current().nextInt()));
				obsMap.put(key, observation);
				this.setParameters(obsMap, newResultSet, jsonNode, key);
			}
		}
		return this.getObservationList(obsMap);
	}

	/**
	 * This method checks the value of the jsonNode and then sets the parameter of the Observation object
	 * in obsMap accordingly.
	 * 
	 * @param obsMap: Map<String, Observation>
	 * @param resultSet: JSONObject
	 * @param jsonNode: String
	 * @param key: String
	 * 
	 * @throws Exception
	 */
	protected void setParameters(Map<String, Observation> obsMap, JSONObject resultSet, String jsonNode, String key)
			throws Exception {

		QuantityDt quantity = new QuantityDt();

		/* Checks if jsonNode matches the specified format, then maps *date* from OpenEHR to FHIR Observation */
		if (jsonNode.equals(key + "-date")) {
			obsMap.get(key).setEffective(new DateTimeDt(resultSet.optString(jsonNode)));
			logger.debug("*** Method: setParameters  | Date of " + key + " = " + resultSet.optString(jsonNode) + " ***");
		
		/* Checks if jsonNode matches the specified format, then maps *magnitude* from OpenEHR to value in FHIR Observation */
		} else if (jsonNode.equals(key + "-magnitude")) {
			String magnitude = resultSet.optString(jsonNode);
			quantity = (QuantityDt) obsMap.get(key).getValue();
			if (magnitude != null && !magnitude.equals("")) {
				quantity.setValue(Double.parseDouble(magnitude));
				logger.debug("*** Method: setParameters  | Value of " + key + " = " + resultSet.optDouble(jsonNode) + " ***");
				obsMap.get(key).setValue(quantity);
			}
			
		/* Checks if jsonNode matches the specified format, then maps *unit* from OpenEHR to FHIR Observation */
		} else if (jsonNode.equals(key + "-units")) {
			quantity = (QuantityDt) obsMap.get(key).getValue();
			quantity.setCode(resultSet.optString(jsonNode));
			quantity.setUnit(resultSet.optString(jsonNode));
			logger.debug("*** Method: setParameters  | Units of " + key + " = " + resultSet.optString(jsonNode) + " ***");
			
		/* Checks if jsonNode matches the specified format, then, maps *value* from OpenEHR to FHIR Observation */
		} else if (jsonNode.equals(key + "-value")) {
			String value = resultSet.optString(jsonNode);
			quantity = (QuantityDt) obsMap.get(key).getValue();
			if (value != null && !value.equals("")) {
				quantity.setValue(OpenEHRConvertor.parsePeriodToMonths(value));
				quantity.setUnit("Months");
				obsMap.get(key).setValue(quantity);
				logger.debug("*** Method: setParameters  | Value of " + key + " = " + resultSet.optString(jsonNode) + " ***");
			}	
		}
		
	}

	/**
	 * This method converts Map<String, Observation> to List<Observation> 
	 * 
	 * @param obsMap : Map<String, Observation>
	 * @return List<Observation>
	 * @throws Exception
	 */
	protected List<Observation> getObservationList(Map<String, Observation> obsMap) throws Exception {

		List<Observation> observationList = new ArrayList<>();
		logger.debug("*** Method: getObservationList | Observations Map readings : ***");
		for (Map.Entry<String, Observation> obs : obsMap.entrySet()) {
			observationList.add(obs.getValue());
			QuantityDt q = (QuantityDt) obs.getValue().getValue();
			logger.debug("---------------------");
			logger.debug("Code: " + obs.getValue().getCode().getCoding().get(0).getCode());
			logger.debug("Text: " + obs.getValue().getCode().getText());
			logger.debug("Magnitude: " + q.getValue());
			logger.debug("Unit Code: " + q.getCode());
			logger.debug("Unit: " + q.getUnit());
			logger.debug("System: " + q.getSystem());
			logger.debug("Date: " + obs.getValue().getEffective());
			logger.debug("Patient Id: " + obs.getValue().getSubject().getReference());
			logger.debug("---------------------");
		}
		return observationList;
	}

	/**
	 * This method removes the substring "LONIC_" from the entire JSONObject received from EHR system as input.
	 * A part of sample response from CDR is like "LONIC_3141_9_magnitude\": 10.9," . So this method removes
	 * "LONIC_" and returns "3141_9_magnitude\": 10.9,".
	 * This is done to prepare the JSONObject for efficient mapping to DSTU2 Observation. Once the specified 
	 * substring is replaced the JSONObject is returned. 
	 * 
	 * @param json: JSONObject
	 * @return JSONObject
	 * @throws Exception
	 */
	protected JSONObject prepareInputJSON(JSONObject json) throws Exception {

		logger.debug("*** Method: prepareInputJSON | Input json *** " + json.toString() + " ***");
		String jsonString = json.toString().replaceAll("LONIC_", "");
		logger.debug("*** Method: prepareInputJSON | Prepared json *** " + jsonString + " ***");
		JSONObject newJSON = new JSONObject(jsonString);
		
		return newJSON;
	}
	
	/**
	 * This method replaces "_" in the input JSONObject with "-".
	 * A part of sample response input to this method is like : "3141_9_magnitude\": 10.9,".
	 * This method converts it to "3141-9-magnitude\": 10.9,". This is done because the 
	 * standard LONIC code for any observation contains "-", for e.g., 3141-9 stands for weight.
	 * 
	 * @param json: JSONObject
	 * @return JSNObject
	 * @throws Exception
	 */
	protected JSONObject prepareResultSet(JSONObject json) throws Exception {
		
		String jsonString = json.toString().replaceAll("_", "-");
		logger.debug("*** Method: prepareResultSet | Prepared resultSet " + jsonString + " ***");
		JSONObject newJSON = new JSONObject(jsonString);
		
		return newJSON;
		
	}
	
	/**
	 * ISO_8601 : As from Wikipedia
	 * P[n]Y[n]M[n]DT[n]H[n]M[n]S or P[n]W
	 * 
	 *  P is the duration designator (for period) placed at the start of the duration representation.
	 *	Y is the year designator that follows the value for the number of years.
	 *	M is the month designator that follows the value for the number of months.
	 *	W is the week designator that follows the value for the number of weeks.
	 *	D is the day designator that follows the value for the number of days.
	 *	T is the time designator that precedes the time components of the representation.
	 *	H is the hour designator that follows the value for the number of hours.
	 *	M is the minute designator that follows the value for the number of minutes.
	 *	S is the second designator that follows the value for the number of seconds.
	 *	For example, "P3Y6M4DT12H30M5S" represents a duration of "three years, six months, four days, twelve hours, thirty minutes, and five seconds".
	 * 
	 * This method only parse the period till month, i.e. P[n]Y[n]M anything after M is discarded. Converts the period 
	 * into months and return the number of months.
	 * @param value : String in ISO_8601 format
	 * @return Double : 
	 * @throws Exception
	 */
	protected static Double parsePeriodToMonths(String value) throws Exception {
		
		String period = ""; 
		logger.debug("*** Method: parsePeriodToMonths | Input period: " + value + " ***");
		if(value.substring(0,1).equalsIgnoreCase("P"))
			period = value.substring(1);

		char[] p = period.toCharArray();
		char[] iso = {'Y', 'M'};
		
		Double months = 0.0;
		List<String> duration = new ArrayList<>(Arrays.asList("", ""));
		
		int j = 0;
		for(int i = 0; i < p.length ; i++){
			if(p[i]!= iso[j]){
				duration.set(j, duration.get(j)+p[i]);
			}
			else
				j++;
			if(j > 1)
				break;
		}
		if(duration.get(0) != null && !duration.get(0).equals("")){
			months = (Double.parseDouble(duration.get(0)) * 12);
		}
		if(duration.get(1) != null && !duration.get(1).equals("")){
			months = months + (Double.parseDouble(duration.get(1)));
		}
		logger.debug("*** Method: parsePeriodToMonths | Months = " + months + " ***");
		return months;
	}
	
}
