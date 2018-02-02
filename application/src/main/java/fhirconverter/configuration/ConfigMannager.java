package fhirconverter.configuration;

import java.util.HashMap;
import java.util.Map;

/**
* This class handles all the interactions with other modules in the system.
* When constructed, it loads all the configuration files of the specified environment as Config objects.
* When recveiving requests, it arranges relevant Config object to handle the requests
*
* @author Abdul-qadir Ali
* @author Chenghui Fan
*/

public class ConfigMannager {
	//Specify environment
	static final String DEVELOPING = "Developing";
	static final String TESTING = "Testing";
	static final String PRODUCTION = "Production";
	
	private String environment;
	private ConfigFilePathMannager filePathMannager;
	
	//structure: <configType, configObject>
	private Map<String, Config>registeredConfig;
	
	public ConfigMannager(String environment){
		this.environment = environment;
		this.registeredConfig = new HashMap<>();
		this.filePathMannager = new ConfigFilePathMannager();
		loadConfig();
	}
	
	public Object getMappingResult(String key) {
		Config mappingConfig = this.registeredConfig.get(Config.MAPPING);
		if(mappingConfig instanceof MappingConfig)
			return ((MappingConfig) mappingConfig).getMappingResult(key);
		else
			return null;
	}
	
	private void loadConfig() {
		Map<String, String> filePaths = this.filePathMannager.getFilePathsByEnvironment(this.environment);
		
		for(String key: filePaths.keySet()) {
			switch(key) {
				case Config.DATABASE:
					break;
				case Config.MAPPING:
					registerMappingConfig(filePaths.get(key));
					break;
			}
		}
	}
	
	private void registerMappingConfig(String filePath) {
		Config mappingConfig = new MappingConfig(filePath);
		registeredConfig.put(Config.MAPPING, mappingConfig);
	}
}
