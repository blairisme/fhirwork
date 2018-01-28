package fhirconverter.configuration;

public abstract class Config {
	private String configType;
	
	public Config(String configType, String filePath) {
		this.configType = configType;
	}
	
	public String getConfigType(){
		return this.configType;
	}
	
	public abstract void addConfig(String key, String value);
	
	public abstract void removeConfig(String key);
	
	public abstract void changeConfig(String key, String value);
}
