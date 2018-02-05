package fhirconverter.configuration;

/**
 * This class defines the config objects. All the different types of configuration files 
 * will be loaded into the system as instances of This class. It stores the configType 
 * and the path of the file that the config object loads.
 * @author Chenghui Fan
 * @author Abdul-Qadir Ali
 * 
 */

public abstract class Config {
	//current supported config types
	static final String MAPPING = "Mapping";
	static final String NETWORK = "Network";
	static final String DATABASE = "Database";
	
	private String configType;
	private String filePath;
	
	//True if the configuration file should be read every time it is used
	//False if the configuration file should only be read once
	private boolean cachingConfig;
	
	public Config(String configType, String filePath, boolean cachingConfig) {
		this.configType = configType;
		this.filePath = filePath;
		this.cachingConfig = cachingConfig;
	}
	
    /**@return String : The location of the configuration file in the file system */
	public String getFilePath(){
		return this.filePath;
	}
	
	public String getConfigType(){
		return this.configType;
	}
	
	public boolean isConfigCached() {
		return this.cachingConfig;
	}
	
	public abstract void update();
}
