package org.ucl.fhirwork.configurationSystem;
import java.io.FileNotFoundException;
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
	
	/**@param environment
	 * @param pathFileLocation - the location of the config path file, <br/> if this parameter remains empty, a default path will be used: <br/> src/main/resources/configFilePath.json*/
	public ConfigMannager(Environment environment){
		this.environment = environment;
		this.registeredConfig = new HashMap<>();
		this.filePathMannager = new ConfigFilePathMannager(ConfigFilePathMannager.DEFAULT_PATH_FILE_LOCATION);
		loadConfig();
	}
	
	/**@param environment
	 * @param pathFileLocation - the location of the config path file, <br/> if this parameter remains empty, a default path will be used: <br/> src/main/resources/configFilePath.json*/
	public ConfigMannager(Environment environment, String pathFileLocation){
		this.environment = environment;
		this.registeredConfig = new HashMap<>();
		this.filePathMannager = new ConfigFilePathMannager(pathFileLocation);
		loadConfig();
	}
	
	/**This method is used for getting mapping result by the key of the requested mapping rule
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
	
	/**@param NetworkConfigType*/
	public String getNetworkAddress(NetworkConfigType networkConfigType) {
		NetworkConfig networkConfig = (NetworkConfig) this.registeredConfig.get(ConfigType.NETWORK);
		String result = networkConfig.getAddress(networkConfigType);
		return result;
	}
	
	/**@param NetworkConfigType*/
	public String getNetworkUsername(NetworkConfigType networkConfigType) {
		NetworkConfig networkConfig = (NetworkConfig) this.registeredConfig.get(ConfigType.NETWORK);
		String result = networkConfig.getUsername(networkConfigType);
		return result;
	}
	
	/**@param NetworkConfigType*/
	public String getNetworkPassword(NetworkConfigType networkConfigType) {
		NetworkConfig networkConfig = (NetworkConfig) this.registeredConfig.get(ConfigType.NETWORK);
		String result = networkConfig.getPassword(networkConfigType);
		return result;
	}
	
	private void loadConfig() {
		Map<ConfigType, String> filePaths = this.filePathMannager.getFilePathsByEnvironment(this.environment);
		for(ConfigType key: filePaths.keySet()) 
			switch(key) {
				case NETWORK:
					registerNetworkConfig(filePaths.get(key));
					break;
				case MAPPING:
					registerMappingConfig(filePaths.get(key));
					break;
				case DATABASE:
					//TODO
					break;
				default:
					break;
			}
	}
	
	private void registerMappingConfig(String filePath) {
		Config mappingConfig = new MappingConfig(filePath, false);
		registeredConfig.put(ConfigType.MAPPING, mappingConfig);
	}
	
	private void registerNetworkConfig(String filePath) {
		Config networkConfig = new NetworkConfig(filePath, false);
		registeredConfig.put(ConfigType.NETWORK, networkConfig);
	}
}
