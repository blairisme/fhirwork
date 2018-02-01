package fhirconverter.configuration;

import java.util.Map;

public class ConfigMannager {
	//Specify environment
	static String DEVELOPING = "Developing";
	static String TESTING = "Testing";
	static String PRODUCTION = "Production";
	
	private int environment;
	private ConfigFilePathMannager configFilePathMannager;
	
	//structure: <environment, <configType, configObject>>
	private Map<Integer, Map<String, Config>>registeredConfig;
	
	public ConfigMannager(int environment){
		this.environment = environment;
		this.configFilePathMannager = new ConfigFilePathMannager();
	}
	
	private void loadConfig() {
		
	}
	
	public Object getMapping() {
		return null;
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
