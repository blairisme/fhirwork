package org.ucl.fhirwork.configurationSystem;

/**
 * This class defines the config objects. All the different types of configuration files 
 * will be loaded into the system as instances of This class. It stores the configType 
 * and the path of the file that the config object loads.
 * @author Chenghui Fan
 * @author Abdul-Qadir Ali
 * 
 */

//TODO: have a list of errors/exceptions that this class should handle

public abstract class Config {
	private ConfigType configType;
	private String filePath;
	
	//True if the configuration file should be read every time it is used
	//False if the configuration file should only be read once
	private boolean cachingConfig;
	
	/**@param configTyoe
	 * @param filePath - the location of the config file
	 * @param cachingConfig - whether the class should cache the config loaded, <br/>
	 * true: config will only be loaded when this class is constructed, <br/>
	 * false: config will be loaded every time it is used*/
	public Config(ConfigType configType, String filePath, boolean cachingConfig) {
		this.configType = configType;
		this.filePath = filePath;
		this.cachingConfig = cachingConfig;
	}
	
    /**@return filePath : The location of the configuration file in the file system */
	public String getFilePath(){
		return this.filePath;
	}
	
	/**@return configType*/
	public ConfigType getConfigType(){
		return this.configType;
	}
	
	/**@return isConfigCached - <br/> true: the config is cached and will only be loaded once when the Config object is constructed; <br/> 
	 * false: the config is not cached and will be loaded every time it is used*/
	public boolean isConfigCached() {
		return this.cachingConfig;
	}
	
	/**This method will be called when the config file is updated*/
	public abstract void update();
}
