package fhirconverter.configuration;
import java.util.HashMap;
import java.util.Map;

/**
<<<<<<< Updated upstream
* This class handles all the interactions with other modules in the system.
* When constructed, it loads all the configuration files of the specified environment as Config objects.
* When recveiving requests, it arranges relevant Config object to handle the requests
*

 * This is the Configuration Manager class.
 * Loads all the configuration files for specific environments config object when constructed
 * When receiving requests, it arranges relevant config objects to handle the request. .
 * Handles all interactions with the other modules in the system.
 
 * @author Chenghui Fan
 * @author Abdul-Qadir Ali
 *
 */
public class ConfigMannager {
	//Specifying the various environments within the software system.
	static String DEVELOPING = "Developing";
	static String TESTING = "Testing";
	static String PRODUCTION = "Production";
	static String MAPPING = "Mapping";
	static String DATABASE = "Database";

	
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
	// Obtaining mapping results in fulfilment of a given request
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
