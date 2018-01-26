package fhirconverter.mapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapperEntry {
	private Map<String, String> entry = null;
	private String type = "";
	public static int EMPI_EHR = 0;
	public static int EHR_EMPI = 1;
	public static int EMPI_FHIR = 2;
	public static int FHIR_EMPI = 3;
	public static int EHR_FHIR = 4;
	public static int FHIR_EHR = 5;
	public static int size = 6;
	
	public Set<String> getKeys(){
		if (entry == null)
			entry = new HashMap<String, String>();
		return entry.keySet();
	}
	
	public Collection<String> getValues(){
		if (entry == null)
			entry = new HashMap<String, String>();
		return entry.values();
	}
	public String get(String key) {
		if(entry.containsKey(key))
			return entry.get(key);
		return null;
	}
	
	public void getFromConfig(int type){

		if (entry == null)
			entry = new HashMap<String, String>();
		else
			entry.clear();
	
		//Config c = createConfig(type)
		
		//extract entry from config and type
		
		//add things from config to entry and set type
		
		
	}
}
