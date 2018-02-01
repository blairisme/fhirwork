package fhirconverter.configuration;

import java.util.Map;

public class ConfigMannager {
	static int DEVELOPING = 0;
	static int TESTING = 1;
	static int PRODUCTION = 2;
	
	private int environment;
	private FilePathMannager filePathMannager;
	private Map<Integer, Config>registeredConfig;
	
	public ConfigMannager(int environment){
		this.environment = environment;
		this.filePathMannager = new FilePathMannager();
	}
	
	public String getConfigFilePathByName(String configName){
		return this.filePathMannager.getFilePath(configName);
	}
	
	public void addConfig(String configType, String filePath, boolean isReadable, boolean isWritable){
		Config config;
		switch(configType){
			case "mapping":
				
		}
	}
	
	private void addConfigToMap(){
		
	}
}
