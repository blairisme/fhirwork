package fhirconverter.configuration;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

/**
 * This class is used for managing the location of the configuration files in the file system,
 * The location of the configuration files is stored in a JSON file at the location defined by
 * the PATH_FILE_LOCATION static variable, the structure of that file is similar to a map in the
 * following format: Map<Environment, Map<ConfigType, String>>, where the possible value of the
 * Environment and ConfigType can be found in the enum classes Environment and ConfigType 
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
