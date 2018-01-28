package fhirconverter.configuration;

public class ConfigMannager {
	static int DEVELOPING = 0;
	static int TESTING = 1;
	static int PRODUCTION = 2;
	
	private int environment;
	private FilePathMannager filePathMannager;
	
	public ConfigMannager(int environment){
		this.environment = environment;
		this.filePathMannager = new FilePathMannager();
	}
	
	public String getConfigFilePathByName(String configName){
		return this.filePathMannager.getFilePath(configName);
	}
}
