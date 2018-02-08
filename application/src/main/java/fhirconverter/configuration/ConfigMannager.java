package fhirconverter.configuration;
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
	
	/**@param environment - the name of the environment*/
	public ConfigMannager(Environment environment){
		this.environment = environment;
		this.registeredConfig = new HashMap<>();
		this.filePathMannager = new ConfigFilePathMannager();
		try {
			loadConfig();
		} catch (FileNotFoundException e) {
			System.out.println("The configuration file is not not reachable");
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	/**This method is used for getting network config
	 * 
	 * @param networkConfigType
	 * @param key - valid keys: "Address", "Username", "Password", Non-case-sensitive
	 * @return value
	 * */
	public String getNetworkConfig(NetworkConfigType networkConfigType, String key) {
		NetworkConfig networkConfig = (NetworkConfig) this.registeredConfig.get(ConfigType.NETWORK);
		if(key.equalsIgnoreCase("address")) {
			return networkConfig.getAddress(networkConfigType);
		}
		else if(key.equalsIgnoreCase("username")){
			return networkConfig.getUsername(networkConfigType);
		}
		else if(key.equalsIgnoreCase("password")) {
			return networkConfig.getPassword(networkConfigType);
		}
		else
			System.out.println("Not a valid key for getting network config");
		return null;
	}
	
	//Not sure if we should provide this accessibility
	/**This method is used for getting config objects
	 * @param configType
	 * @return configObject
	 * */
	public Config getConfig(ConfigType configType) {
		return this.registeredConfig.get(configType);
	}
	
	private void loadConfig() throws FileNotFoundException {
		Map<ConfigType, String> filePaths = this.filePathMannager.getFilePathsByEnvironment(this.environment);
		for(ConfigType key: filePaths.keySet()) {
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
