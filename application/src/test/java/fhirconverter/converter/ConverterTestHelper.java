package fhirconverter.converter;

import org.json.JSONArray;
import org.json.JSONObject;

public class ConverterTestHelper {
	static int FAMILYNAME = 0;
	static int GIVENNAME = 1;
	static int MAIDENNAME = 2;
	static int MIDDLE = 3;
	
	public String getName(JSONObject patient, int code) {
		String expectedFamilyName = "";
		String expectedGivenName = "";
		String expectedMaidenName = "";
		String expectedMiddle = "";
		
		JSONArray names = patient.getJSONArray("name");
		for(int i=0; i<names.length(); i++) {
			if("usual".equals(names.getJSONObject(i).optString("use"))) {
				expectedFamilyName = names.getJSONObject(i).getJSONArray("family").getString(0);
				if(names.getJSONObject(i).getJSONArray("given").length()>1) {
					expectedMiddle = names.getJSONObject(i).getJSONArray("given").getString(1);							
				} 
				expectedGivenName = names.getJSONObject(i).getJSONArray("given").getString(0);
			}
			if("maiden".equals(names.getJSONObject(i).optString("use"))) {
				expectedMaidenName = names.getJSONObject(i).getJSONArray("family").getString(0);
			}
			
		}
		if(code==FAMILYNAME)
			return expectedFamilyName;
		if(code==GIVENNAME)
			return expectedGivenName;
		if(code==MAIDENNAME)
			return expectedMaidenName;
		else
			return expectedMiddle;
		
	}
}
