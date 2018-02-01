package fhirconverter.configuration;

import java.util.HashMap;
import java.util.Map;

public class ConfigMannager {
	//Specify environment
	static String DEVELOPING = "Developing";
	static String TESTING = "Testing";
	static String PRODUCTION = "Production";
	static String MAPPING = "Mapping";
	static String DATABASE = "Database";
	
	private String environment;
	
	//structure: <environment, <configType, configObject>>
	private Map<String, Map<String, Config>>registeredConfig;
	
	public ConfigMannager(String environment){
		this.environment = environment;
		
	}
	
	@SuppressWarnings("unused")
	private void loadConfig(String environment) {
		
	}
	
	public Object getMappingResult(String key) {
		Config mappingConfig = this.registeredConfig.get(this.environment).get(MAPPING);
		if(mappingConfig instanceof MappingConfig)
			return ((MappingConfig) mappingConfig).getMappingResult(key);
		else
			return null;
	}
	
}
