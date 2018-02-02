package fhirconverter.configuration;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

/**
 * This class handles all the interactions with other modules in the system.
 * When constructed, it loads all the configuration files of the specified environment as Config objects.
 * When recveiving requests, it arranges relevant Config object to handle the requests
 *
 * @author Abdul-qadir Ali
 * @author Chenghui Fan
 */

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
	
	@SuppressWarnings("unchecked")
	private Map<String, Map<String, String>> loadFilePath(){
		gsonSerializer serializer = new gsonSerializer();
		Type type = new TypeToken<Map<String, Map<String, String>>>() {}.getType(); 
		Map<String, Map<String, String>> convertedFilePath = (Map<String, Map<String, String>>) serializer.fromJsonFileToSpecifiedTypeObj(type, this.pathFileLocation);
		return convertedFilePath;
	}
}
