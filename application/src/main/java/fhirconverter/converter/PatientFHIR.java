package fhirconverter.converter;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import fhirconverter.exceptions.FhirSchemeNotMetException;
import fhirconverter.exceptions.OpenEMPISchemeNotMetException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.util.List;

public class PatientFHIR {
    private Logger LOGGER = LogManager.getLogger(PatientFHIR.class);
	OpenEMPIConnector caller = new OpenEMPIConnector();

    public Patient read(String id) throws Exception {
        String result = caller.readPerson(id);
        ConversionOpenEmpiToFHIR converter = new ConversionOpenEmpiToFHIR();
        return converter.conversion(result).get(0);
    }

    public List<Patient> search(JSONObject parameters) throws Exception {
        String result = "";
        if((parameters.has("identifier_value"))&&(parameters.has("identifier_domain"))) {
            result = caller.searchPersonById(parameters);
        }else
            result = caller.searchPersonByAttributes(parameters);

        LOGGER.debug("Search Results: " + result);

        ConversionOpenEmpiToFHIR converter = new ConversionOpenEmpiToFHIR();
        List<Patient> patients = converter.conversion(result);
        LOGGER.debug("Patients Converted: " + patients);
        return patients;
    }

    public String update(String id, JSONObject patient) throws Exception {
        ConversionFHIRToOpenEmpi converter = new ConversionFHIRToOpenEmpi();
        JSONObject newRecordOpenEMPI = converter.conversionToOpenEMPI(patient);
        newRecordOpenEMPI.put("personId", id);

        JSONObject records = new JSONObject();

        records.put("person", newRecordOpenEMPI);

        LOGGER.debug(records);

        /**
         * For now we don't handle the update of personIdentifiers, so
         * if they are included in the JSONObject we will ignore it
         */

        String xmlNewRecord = XML.toString(records);
        LOGGER.debug("SEND TO OPENEMPIBASE: \n" + xmlNewRecord );
        String result = caller.updatePerson(xmlNewRecord);
		/*ConversionOpenEMPI_to_FHIR converterOpenEmpi = new ConversionOpenEMPI_to_FHIR();
		JSONObject createdObject = converterOpenEmpi.conversion(result);
		String replyCreatedNewRecord = "";
		if(createdObject.has("id")){
			replyCreatedNewRecord = createdObject.getString("id");	
		}*/
        return result; // Ask yuan if he wants all the fields or just certain.

    }

    private JSONObject createIdentifierOpenEMPI(JSONObject identifierRecord) {
        JSONObject identifier = new JSONObject();
        identifier.put("identifier", identifierRecord.optString("identifier"));
        JSONObject identifierDomain = new JSONObject();
        identifierDomain.put("identifierDomainName", identifierRecord.getJSONObject("identifierDomain").optString("identifierDomainName"));
        identifierDomain.put("identifierDomainId", identifierRecord.getJSONObject("identifierDomain").optString("identifierDomainId"));
        identifier.put("identifierDomain", identifierDomain);
        return identifier;
    }

    public String patch(String id, JsonPatch patient) throws Exception { //more testing needed! only gender done. by koon
        String result = caller.readPerson(id);
        ConversionOpenEmpiToFHIR converterOpenEmpi = new ConversionOpenEmpiToFHIR();
//		JSONObject xmlResults = converterOpenEmpi.conversion(result);
        Patient patientObj = converterOpenEmpi.conversion(result).get(0);
        JSONObject xmlResults = new JSONObject(FhirContext.forDstu2().newJsonParser().encodeResourceToString(patientObj));

        xmlResults.remove("identifier");
        String jsonResults = xmlResults.toString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNodeResults = mapper.readTree(jsonResults);
        JsonNode patched = null;
        try{
            patched = patient.apply(jsonNodeResults);
        }catch(Exception e){
            e.printStackTrace();
            throw new JsonPatchException("Resource does not contain the paths for remove or replace");
        }
        if(patched != null)
        {
            LOGGER.debug(patched.toString());
            boolean fhirSchemeRequirements = Utils.validateScheme(patched, "resource/Patient.schema.json");
            if(fhirSchemeRequirements){
                JSONObject patchedResults = new JSONObject(patched.toString());
                ConversionFHIRToOpenEmpi converterFHIR = new ConversionFHIRToOpenEmpi();
                JSONObject convertedXML = converterFHIR.conversionToOpenEMPI(patchedResults);
                final JsonNode jsonNodePatched = mapper.readTree(convertedXML.toString());
                if(Utils.validateScheme(jsonNodePatched, "resource/openempiSchema.json")){
                    JSONObject convertedXMLvalidated = new JSONObject();
                    JSONObject xmlJsonObj = XML.toJSONObject(result);
                    JSONObject xmlPeopleObj = xmlJsonObj.getJSONObject("person");
                    System.out.println(xmlPeopleObj.toString());
                    JSONArray personIdentifiers = xmlPeopleObj.optJSONArray("personIdentifiers");
                    if(personIdentifiers == null){
                        JSONObject personIdentifier = xmlPeopleObj.getJSONObject("personIdentifiers");
                        convertedXML.put("personIdentifiers", personIdentifier);

                    }else{
                        JSONArray multipleIdentifiers = xmlPeopleObj.getJSONArray("personIdentifiers");
                        convertedXML.put("personIdentifiers", multipleIdentifiers);
                    }

                    convertedXML.put("personId", id);
                    convertedXMLvalidated.put("person", convertedXML);
                    final String xmlPatch = XML.toString(convertedXMLvalidated);
                    return caller.updatePerson(xmlPatch);
                }else{
                    throw new OpenEMPISchemeNotMetException("The Parameters does not confine to OpenEMPIScheme");
                }
            }else{
                throw new FhirSchemeNotMetException("The parameters does not confine to FHIR Standards for OpenEMPIScheme");
            }

        }
        else
        {
            throw new Exception("Patch operators are empty!");
        }
    }

    public String create(JSONObject patient) throws Exception{
        ConversionFHIRToOpenEmpi converter = new ConversionFHIRToOpenEmpi();
        JSONObject newRecordOpenEMPI = converter.conversionToOpenEMPI(patient);
        JSONObject records = new JSONObject();
        records.put("person", newRecordOpenEMPI);
        String xmlNewRecord = XML.toString(records);
        String result = caller.addPerson(xmlNewRecord);
        ConversionOpenEmpiToFHIR converterOpenEmpi = new ConversionOpenEmpiToFHIR();
//		JSONObject createdObject = converterOpenEmpi.conversion(result);
        Patient createdPatient = converterOpenEmpi.conversion(result).get(0);
        String replyCreatedNewRecord = "";
//		if(createdObject.has("id")){
//			replyCreatedNewRecord = createdObject.getString("id");
//		}
        if(createdPatient.getId() != null)
        {
            replyCreatedNewRecord = createdPatient.getId().getIdPart();
        }
        return replyCreatedNewRecord; // Ask yuan if he wants all the fields or just certain.
    }

    public String delete(String id) throws Exception {
        String result = caller.deletePersonById(id);
        return result;
        //return "";
    }

}
