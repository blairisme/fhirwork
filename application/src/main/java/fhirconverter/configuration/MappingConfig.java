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
	private Map<String, String> codeMap;
	
	public MappingConfig(String filePath){
		super("Mapping", filePath);
		this.codeMap = new HashMap<>();
		loadMappingConfig();
	}
	
	@SuppressWarnings("unchecked")
	private void loadMappingConfig(){
		ObjectMapper mapper = new ObjectMapper(); 
		File file = new File(this.getFilePath());
		try {
			this.codeMap = mapper.readValue(file, HashMap.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Map<String, String> getMappingConfig(){
		return this.codeMap;
	}

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
