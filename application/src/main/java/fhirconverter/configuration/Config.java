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
	static final String DATABASE = "Database";
	
	private String configType;
	private String filePath;
	
	public Config(String configType, String filePath) {
		this.configType = configType;
		this.filePath = filePath;
	}
	
    /**@return String : The location of the configuration file in the file system */
	public String getFilePath(){
		return this.filePath;
	}
	
	public void setFilePath(String newPath){
		this.filePath = newPath;
	}
	
	public String getConfigType(){
		return this.configType;
	}
	
	//methods below will be used for supporting the modification to the configuration file
	//different config objects would have different implementation
	
	public abstract void addConfig(String key, Object value);
	
	public abstract void removeConfig(String key);
	
	public abstract void changeConfig(String key, Object value);
	
}
