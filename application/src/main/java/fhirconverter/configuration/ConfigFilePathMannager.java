package fhirconverter.configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

//in progress by Chenghu Fan
public class ConfigFilePathMannager {
	//Specify environment
	static String DEVELOPING = "Developing";
	static String TESTING = "Testing";
	static String PRODUCTION = "Production";
	
	private String pathFileLocation = "src/main/resources/configFilePath.json";
	
	//structure <environment, <configFileType, path>>
	private Map<String, Map<String, String>> filePath;
	
	public ConfigFilePathMannager(String pathFileLocation) {
		this.pathFileLocation = pathFileLocation;
		this.filePath = this.loadFilePath();
	}
	
	public ConfigFilePathMannager(){
		this.filePath = loadFilePath();
	}
	
	public String getPathFileLocation() {
		return this.pathFileLocation;
	}
	
	public void setPathFileLocation(String pathFileLocation) {
		this.pathFileLocation = pathFileLocation;
	}
	
	public String getFilePath(String environment, String fileName){
		return filePath.get(environment).get(fileName);
	}
	
	//TODO: throw specific exception
	@SuppressWarnings("unchecked")
	private Map<String, Map<String, String>> loadFilePath(){
		Map<String, Map<String, String>>inputFilePath = new HashMap<>();
		File file = new File(this.pathFileLocation);
		ObjectMapper mapper = new ObjectMapper(); 
		try {
			inputFilePath = mapper.readValue(file, HashMap.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			inputFilePath = null;
		}
        return inputFilePath;
	}
}
