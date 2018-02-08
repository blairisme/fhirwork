package org.ucl.fhirwork.configurationSystem;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.reflect.TypeToken;

/**
 * This class extends the Config class. Responsible for handling request for mapping results,
 * fetching data from mapping configuration file and supporting operations to the mapping
 * configuration file.
 * 
 * @author Abdul-qadir Ali
 * @author Chenghui Fan
 */

//TODO: have a list of errors/exceptions that this class should handle

public class MappingConfig extends Config {
	
	//Stores the mapping configuration loaded from file
	//Structure <key(LONIC code), value(String/jsonObject)>
	private Map<String, Object> codeMap;
	
	/**@param filePath - the location of the config file
	 * @param cachingConfig - whether the class should cache the config loaded, 
	 * true: config will only be loaded when this class is constructed,
	 * false: config will be loaded every time it is used*/
	public MappingConfig(String filePath, boolean cachingConfig){
		super(ConfigType.MAPPING, filePath, cachingConfig);
		initialize();
	}
	
	private void initialize() {
		if(this.isConfigCached()) {
			this.codeMap = new HashMap<>();
			this.codeMap = loadMappingConfig();			
		}
	}
	
	//load mapping config from file and convert the content to java Map<String, Object> object 
	@SuppressWarnings("unchecked")
	private Map<String, Object> loadMappingConfig(){
		JsonSerializer serializer = new JsonSerializer();
		
		//define the type of the object that would be converted to (should be supported by gson fromJson method)
		Type type = new TypeToken<Map<String, Object>>() {}.getType(); 
		
		Map<String, Object> convertedMappingConfig = (Map<String, Object>) serializer.fromJsonFileToObj(type, this.getFilePath());
		if(convertedMappingConfig == null) {
			System.out.println("loading mapping configuration file failed");
			return null;
		}
		else
			return convertedMappingConfig;
	}
	
	/**This method is used for get mapping result by the key of the requested mapping rule
	 * 
	 * @param key - the key of the requested mapping
	 * @return Object - the requested mapping result <br/> The method returns null if no match found
	 * */
	public Object getMappingResult(String key) {
		if(this.isConfigCached())
			return this.codeMap.get(key);
		else
			return loadMappingConfig().get(key);
	}

	@Override
	public void update() {
		initialize();
	}
	
	//The methods below support modification to the configuration file/database through JavaFX application
	//If the GUI is decided to be made as a webpage, these methods can be removed
	//TODO: refactoring, considering to be made into abstract methods

	/**This method is used for removing a config item in the mapping configuration file
	 * @param key - the key of the config item
	 * */
	public void removeConfig(String key) {
		Map<String, Object> newConfigMap = this.isConfigCached()? this.codeMap : loadMappingConfig();
		newConfigMap.remove(key);
		writeToConfigFile(newConfigMap);
	}

	/**This method is used for adding a config item to the mapping configuration file
	 * @param key - the key of the config item
	 * @param value - the value of the config item
	 * */
	public void addConfig(String key, Object value) {
		Map<String, Object> newConfigMap = this.isConfigCached()? this.codeMap : loadMappingConfig();
		newConfigMap.put(key, value);
		writeToConfigFile(newConfigMap);
	}
	
	/**This method is used for chaging the value of a config item in the mapping configuration file
	 * @param key - the key of the config item
	 * @param value - the value of the config item
	 * */
	public void changeConfig(String key, Object value) {
		Map<String, Object> newConfigMap = this.isConfigCached()? this.codeMap : loadMappingConfig();
		newConfigMap.put(key, value);
		writeToConfigFile(newConfigMap);
	}
	
	private void writeToConfigFile(Map<String, Object> newConfigMap) {
		JsonSerializer serializer = new JsonSerializer();
		Type type = new TypeToken<Map<String, Object>>() {}.getType(); 
		serializer.fromObjToJsonFile(type, newConfigMap, this.getFilePath());
	}
}
