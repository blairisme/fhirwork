package fhirconverter.configuration;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

/**
 * (Description for this class)
 *
 * @author Abdul-qadir Ali
 * @author Chenghui Fan
 */


public class MappingConfig extends Config {
	//Stores the mapping configuration loaded from file
	private Map<String, Object> codeMap;
	
	public MappingConfig(String filePath){
		super(MAPPING, filePath);
		this.codeMap = new HashMap<>();
		loadMappingConfig();
	}
	
	//load configuration file content into Map<String, Object>codeMap
	@SuppressWarnings("unchecked")
	private void loadMappingConfig(){
		gsonSerializer serializer = new gsonSerializer();
		Type type = new TypeToken<Map<String, Object>>() {}.getType(); 
		Map<String, Object> convertedMappingConfig = (Map<String, Object>) serializer.fromJsonFileToSpecifiedTypeObj(type, this.getFilePath());
		if(convertedMappingConfig == null)
			System.out.println("loading mapping configuration file failed");
		else
			this.codeMap = convertedMappingConfig;
	}
	
	//get all the mapping configuration, probably not useful and may be deleted in the future
	public Map<String, Object> getMappingConfig(){
		return this.codeMap;
	}
	
	//configMannager will call this method when receiving a searching request
	public Object getMappingResult(String key) {
		return this.codeMap.get(key);
	}

	//the methods below support modification to the configuration file/database when UI is implemented
	//unfinished
	
	@Override
	public void addConfig(String key, String value) {
		// TODO Auto-generated method stub
	}

	@Override
	public void removeConfig(String key) {
		// TODO Auto-generated method stub
	}

	@Override
	public void changeConfig(String key, String value) {
		// TODO Auto-generated method stub
	}
}
