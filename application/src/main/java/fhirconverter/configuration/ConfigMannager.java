package fhirconverter.configuration;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the Configuration Manager class.
 * Loads all the configuration files for specific environments Config object when constructed
 * When receiving requests, it arranges relevant Config objects to handle the requests.
 * Responsible for the interactions with the other modules in the system.
 * 
 * @author Chenghui Fan
 * @author Abdul-Qadir Ali
 *
 */
public class ConfigMannager {
	//predefined names for some specific environments
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
	
	// Obtaining mapping results in fulfillment of a given request
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
