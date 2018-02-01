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
	private ConfigFilePathMannager configFilePathMannager;
	
	//structure: <environment, <configType, configObject>>
	private Map<String, Map<String, Config>>registeredConfig;
	
	public ConfigMannager(String environment){
		this.configFilePathMannager = new ConfigFilePathMannager();
		
	}
	
	private void loadConfig(String environment) {
		MappingConfig mappingConfig = new MappingConfig(this.configFilePathMannager.getFilePath(this.environment, MAPPING));
		if(mappingConfig == null) {
			System.out.println("mappingConfig loading failed");
		}
		else {
			Map<String, Config> mappingConfigs = this.registeredConfig.get(MAPPING);
			mappingConfigs = mappingConfigs == null ? new HashMap<>() : mappingConfigs;
			mappingConfigs.put(MAPPING, mappingConfig);
			this.registeredConfig.put(this.environment, mappingConfigs);
		}
	}
	
	public Object getMappingResult(String key) {
		Config mappingConfig = this.registeredConfig.get(this.environment).get(MAPPING);
		if(mappingConfig instanceof MappingConfig)
			return ((MappingConfig) mappingConfig).getMappingResult(key);
		else
			return null;
	}
	
}
