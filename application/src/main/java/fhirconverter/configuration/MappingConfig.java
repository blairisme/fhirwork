package fhirconverter.configuration;
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

//TODO null value handling - Chenghui Fan 2018/2/4
public class MappingConfig extends Config {
	//Stores the mapping configuration loaded from file
	//Structure <key(LONIC code), value(String/jsonObject)>
	private Map<String, Object> codeMap;
	
	public MappingConfig(String filePath, boolean cachingConfig){
		super(MAPPING, filePath, cachingConfig);
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

	//the methods below support modification to the configuration file/database through JavaFX application

	public void removeConfig(String key) {
		Map<String, Object> newConfigMap = this.isConfigCached()? this.codeMap : loadMappingConfig();
		newConfigMap.remove(key);
		writeToConfigFile(newConfigMap);
	}

	public void addConfig(String key, Object value) {
		Map<String, Object> newConfigMap = this.isConfigCached()? this.codeMap : loadMappingConfig();
		newConfigMap.put(key, value);
		writeToConfigFile(newConfigMap);
	}

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

	@Override
	public void update() {
		initialize();
	}
}
