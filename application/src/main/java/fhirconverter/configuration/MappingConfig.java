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


public class MappingConfig extends Config {
	//Stores the mapping configuration loaded from file
	//Structure <key(LONIC code), value(String/jsonObject)>
	private Map<String, Object> codeMap;
	
	public MappingConfig(String filePath){
		super(MAPPING, filePath);
		this.codeMap = new HashMap<>();
		this.codeMap = loadMappingConfig();
	}
	
	/**This method load the mapping configuration file (a JSON file) from the file system, 
	 * using gson serializer to turn the format from JSON file to java Map object, the result
	 * will be stored in the codeMap variable.
	 * 
	 * @return Mapping configuration - The mapping configuration in the format of java Map <br/>
	 * 				Returns null if the conversion from json file to Map object failed.
	 * */
	@SuppressWarnings("unchecked")
	private Map<String, Object> loadMappingConfig(){
		gsonSerializer serializer = new gsonSerializer();
		Type type = new TypeToken<Map<String, Object>>() {}.getType(); 
		Map<String, Object> convertedMappingConfig = (Map<String, Object>) serializer.fromJsonFileToSpecifiedTypeObj(type, this.getFilePath());
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
	 * @return Object - the requested mapping result
	 * */
	public Object getMappingResult(String key) {
		return this.codeMap.get(key);
	}

	//the methods below support modification to the configuration file/database when UI is implemented
	//unfinished

	@Override
	public void removeConfig(String key) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addConfig(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeConfig(String key, Object value) {
		// TODO Auto-generated method stub
		
	}
}
