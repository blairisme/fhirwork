package org.ucl.fhirwork.mapping.query;

import org.json.JSONObject;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class QueryBuilder
{
    public String getQuery(String ehrIdentifier, List<String> searchParams)
    {
        String aqlPathText = "{\n" +
                "   \"3141-9\": {\n" +
                "      \"path\": {\n" +
                "         \"date\": \"data[at0002]/origin/value\",\n" +
                "         \"magnitude\": \"data[at0002]/events[at0003]/data[at0001]/items[at0004]/value/magnitude\",\n" +
                "         \"units\": \"data[at0002]/events[at0003]/data[at0001]/items[at0004]/value/units\"\n" +
                "      },\n" +
                "      \"archetype\": \"openEHR-EHR-OBSERVATION.body_weight.v1\",\n" +
                "      \"text\": \"body_weight\"\n" +
                "   }\n" +
                "}\n";

        JSONObject aqlJSONObj = new JSONObject(aqlPathText);
        JSONObject aqlFilteredObj = filterPathsByParams(aqlJSONObj, searchParams);
        String aqlQuery = constructDynamicAQLquery(ehrIdentifier, aqlFilteredObj, searchParams);
        return aqlQuery;
    }

    private String constructDynamicAQLquery(String ehrNumber, JSONObject aqlFilteredObj, List<String> searchParams){
        String selectString = "select";
        String fromString = " from EHR [ehr_id/value='"+ehrNumber+"'] contains COMPOSITION c" ;
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
        return constructedAQLString;
    }

    private JSONObject filterPathsByParams(JSONObject aqlPaths, List<String> searchParams){
        JSONObject filteredPath = new JSONObject();
        for(String searchParam : searchParams){
            if(aqlPaths.has(searchParam)){
                filteredPath.put(searchParam, aqlPaths.getJSONObject(searchParam));
            }
        }
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
