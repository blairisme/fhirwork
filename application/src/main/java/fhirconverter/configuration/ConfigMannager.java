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

//TODO: have a list of errors/exceptions that this class should handle

public class ConfigMannager {
	private Environment environment;
	private ConfigFilePathMannager filePathMannager;
	
	//structure: <configType, configObject>
	private Map<ConfigType, Config>registeredConfig;
	
	/**@param environment - the name of the environment*/
	public ConfigMannager(Environment environment){
		this.environment = environment;
		this.registeredConfig = new HashMap<>();
		this.filePathMannager = new ConfigFilePathMannager();
		loadConfig();
	}
	
	/**This method is used for get mapping result by the key of the requested mapping rule
	 * 
	 * @param key - the key of the requested mapping
	 * @return Object - the requested mapping result <br/> The method returns null if no match found or no mapping Config has been successfully loaded
	 * */
	public Object getMappingResult(String key) {
		Config mappingConfig = this.registeredConfig.get(ConfigType.MAPPING);
		if(mappingConfig != null && mappingConfig instanceof MappingConfig)
			return ((MappingConfig) mappingConfig).getMappingResult(key);
		else
			return null;
	}
	
	private void loadConfig() {
		Map<ConfigType, String> filePaths = this.filePathMannager.getFilePathsByEnvironment(this.environment);
		
		for(ConfigType key: filePaths.keySet()) {
			switch(key) {
				case NETWORK:
					//TODO
					break;
				case MAPPING:
					registerMappingConfig(filePaths.get(key));
					break;
				case DATABASE:
					//TODO
				default:
					break;
			}
		}
	}
	
	private void registerMappingConfig(String filePath) {
		Config mappingConfig = new MappingConfig(filePath, false);
		registeredConfig.put(ConfigType.MAPPING, mappingConfig);
	}
}
