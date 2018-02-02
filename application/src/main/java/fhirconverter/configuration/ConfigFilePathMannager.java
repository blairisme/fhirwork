package fhirconverter.configuration;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

public class ConfigFilePathMannager {

	static final String PATH_FILE_LOCATION = "src/main/resources/configFilePath.json";
	
	//structure <environment, <configFileType, path>>
	private Map<String, Map<String, String>> filePath;
	
	public ConfigFilePathMannager(){
		this.filePath = loadFilePath();
	}
	
	public Map<String, String> getFilePathsByEnvironment(String environment){
		return filePath.get(environment);
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Map<String, String>> loadFilePath(){
		gsonSerializer serializer = new gsonSerializer();
		Type type = new TypeToken<Map<String, Map<String, String>>>() {}.getType(); 
		Map<String, Map<String, String>> convertedFilePath = (Map<String, Map<String, String>>) serializer.fromJsonFileToSpecifiedTypeObj(type, PATH_FILE_LOCATION);
		return convertedFilePath;
	}
}
