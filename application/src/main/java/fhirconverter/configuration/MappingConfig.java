package fhirconverter.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.google.gson.annotations.Expose;

public class MappingConfig extends Config {
	//Stores the mapping configuration loaded from file
	private Map<String, Object> codeMap;
	
	public MappingConfig(String filePath){
		super("Mapping", filePath);
		this.codeMap = new HashMap<>();
		loadMappingConfig();
	}
	
	//load configuration file content into Map<String, Object>codeMap, currently using Jackson 
	//TODO: use gson instead
	@SuppressWarnings("unchecked")
	private void loadMappingConfig(){
		ObjectMapper mapper = new ObjectMapper(); 
		File file = new File(this.getFilePath());
		try {
			this.codeMap = mapper.readValue(file, HashMap.class);
		} catch (IOException e) {
			// TODO throw specific type of exception
			e.printStackTrace();
		}
	}
	
	//get all the mapping configuration, probably not useful and may be deleted in the future
	public Map<String, Object> getMappingConfig(){
		return this.codeMap;
	}
	
	//configMannager will call this method when receiving a searching request
	public Object getMappingResult(String key) {
		return this.codeMap.get(key);
	}

	//the methods below support modification to the configuration file/database when UI is implemented
	//currently not used
	
	@Override
	public void addConfig(String key, String value) {
		// TODO Auto-generated method stub
	}

	@Override
	public void removeConfig(String key) {
		// TODO Auto-generated method stub
	}

	@Override
	public void changeConfig(String key, String value) {
		// TODO Auto-generated method stub
	}
}
