package fhirconverter.configuration;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

//TODO: have a list of errors/exceptions that this class should handle

public class NetworkConfig extends Config {
	//structure <networkConfigtype <key, value>>
	//key: Address, Username, Password
	private Map<String, Map<String, String>> networkConfig;
	
	public NetworkConfig(String filePath, boolean cachingConfiguration) {
		super(ConfigType.NETWORK, filePath, cachingConfiguration);
		initialize();
	}

	private void initialize() {
		if(this.isConfigCached()) {
			this.networkConfig = new HashMap<>();
			this.networkConfig = loadNetworkConfig();			
		}
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Map<String, String>> loadNetworkConfig(){
		JsonSerializer serializer = new JsonSerializer();
		
		//define the type of the object that would be converted to (should be supported by gson fromJson method)
		Type type = new TypeToken<Map<String, Object>>() {}.getType(); 
		
		Map<String, Map<String, String>> convertedMappingConfig = (Map<String, Map<String, String>>) serializer.fromJsonFileToObj(type, this.getFilePath());
		if(convertedMappingConfig == null) {
			System.out.println("loading network configuration file failed");
			return null;
		}
		else
			return convertedMappingConfig;
	}
	
	
	
	public String getAddress() {
		return null;
	}
	
	public String getUsername() {
		return null;
	}
	
	public String getPassword() {
		return null;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
	}
}
