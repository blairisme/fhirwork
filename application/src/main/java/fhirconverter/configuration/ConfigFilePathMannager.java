package fhirconverter.configuration;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

/**
 * (Description for this class)
 *
 * @author Abdul-qadir Ali
 * @author Chenghui Fan
 */

public class ConfigFilePathMannager {

	static final String PATH_FILE_LOCATION = "src/main/resources/configFilePath.json";
	
	//structure <environment, <configFileType, path>>
	private Map<Environment, Map<ConfigType, String>> filePath;
	
	public ConfigFilePathMannager(){
		this.filePath = loadFilePath();
	}
	
	/**This method is for getting all the locations of the configuration files that belong to
	 * the specified environment
	 *
	 * @param environment - The name of the environment
	 * @return filePaths - A java Map object that stores the locations of the configuration files <br/>
	 * The structure of the returned Map: (key: configFileType) (value: the location of the configFile) <br/>
	 * This method returns null if no records of the configuration file locations that belong to the specified 
	 * environment has been found 
	 * */
	public Map<ConfigType, String> getFilePathsByEnvironment(Environment environment){
		return filePath.get(environment);
	}
	
	@SuppressWarnings("unchecked")
	private Map<Environment, Map<ConfigType, String>> loadFilePath(){
		JsonSerializer serializer = new JsonSerializer();
		Type type = new TypeToken<Map<Environment, Map<ConfigType, String>>>() {}.getType(); 
		Map<Environment, Map<ConfigType, String>> convertedFilePath = (Map<Environment, Map<ConfigType, String>>) serializer.fromJsonFileToObj(type, PATH_FILE_LOCATION);
		return convertedFilePath;
	}
}
