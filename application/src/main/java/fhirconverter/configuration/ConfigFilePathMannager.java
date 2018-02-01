package fhirconverter.configuration;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

//in progress by Chenghu Fan
public class ConfigFilePathMannager {
	//Specify environment
	static String DEVELOPING = "Developing";
	static String TESTING = "Testing";
	static String PRODUCTION = "Production";
	
	private String pathFileLocation = "src/main/resources/configFilePath.json";
	
	//structure <environment, <configFileType, path>>
	private Map<String, Map<String, String>> filePath;
	
	public ConfigFilePathMannager(String pathFileLocation) {
		this.pathFileLocation = pathFileLocation;
		this.filePath = this.loadFilePath();
	}
	
	public ConfigFilePathMannager(){
		this.filePath = loadFilePath();
	}
	
	public String getPathFileLocation() {
		return this.pathFileLocation;
	}
	
	public void setPathFileLocation(String pathFileLocation) {
		this.pathFileLocation = pathFileLocation;
	}
	
	public Map<String, String> getFilePathsByEnvironment(String environment){
		return filePath.get(environment);
	}
	
	//TODO: throw specific exception
	private Map<String, Map<String, String>> loadFilePath(){
		gsonSerializer serializer = new gsonSerializer();
		Type type = new TypeToken<Map<String, Map<String, String>>>() {}.getType(); 
		@SuppressWarnings("unchecked")
		Map<String, Map<String, String>> convertedFilePath = (Map<String, Map<String, String>>) serializer.fromJsonFileToSpecifiedTypeObj(type, this.pathFileLocation);
		return convertedFilePath;
	}
}
