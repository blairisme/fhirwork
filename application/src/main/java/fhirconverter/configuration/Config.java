package fhirconverter.configuration;

/**
 * All the different types of configuration files will be loaded into system as
 * instance of objects in this class, stores the configType and the path of the file
 * that the config object loads.
 * instance of objects in this class
 
 * @author Chenghui Fan
 * @author Abdul-Qadir Ali
 * 
 */

//	Considering having extra features that define the accessibility of the config file. (By Chenghui Fan 18/02/02)
public abstract class Config {
	static final String MAPPING = "Mapping";
	static final String DATABASE = "Database";
	
	private String configType;
	private String filePath;
//	private boolean isReadable;
//	private boolean isWritable;
	
// A constructor that initialises the config class.	
//	public Config(String configType, String filePath, boolean isReadable, boolean isWritable) {
	
	public Config(String configType, String filePath) {
		this.configType = configType;
		this.filePath = filePath;
		
//		this.isReadable = isReadable;
//		this.isWritable = isWritable;
	}
	
	// 
	public String getFilePath(){
		return this.filePath;
	}
	
	public void setFilePath(String newPath){
		this.filePath = newPath;
	}
	
	public String getConfigType(){
		return this.configType;
	}
	
	public abstract void addConfig(String key, String value);
	
	public abstract void removeConfig(String key);
	
	public abstract void changeConfig(String key, String value);
	
/*	public boolean isReadable(){
		return this.isReadable;
	}
	
	public boolean isWritable(){
		return this.isWritable;
	}
	
	public void setReadPermission(boolean readPermission){
		this.isReadable = readPermission;
	}
	
	public void setWritePermission(boolean writePermission){
		this.isWritable = writePermission;
	}
*/
}
