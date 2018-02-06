package fhirconverter.configuration;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.Configuration;

public class ConfigMan {

	private String environment;
	//private ConfigFilePathMannager filePathMan;

    private Map<String, Config>registeredConfig;

    public ConfigMan(String environment)
    {
	    this.environment = environment;
        this.registeredConfig= new HashMap<>();
        registeredConfig.put(Configuration.Empi, new NetworkConfiguration("http://localhost:8080", "admin", "admin"));
        registeredConfig.put(Configuration.Ehr, new NetworkConfiguration("https://test.operon.systems/rest/v1", "oprn_jarrod", "ZayFYCiO644"));
        //values.put(Configuration.Ehr,  new NetworkConfiguration("http://localhost:8888/rest/v1", "guest", "guest"));
        loadConfig();
    }

    @SuppressWarnings("unchecked")
    public Object getMappingResult(String key)
    {
        Config mappingConfig = this.registeredConfig.get(ConfigType.MAPPING);
		if(mappingConfig != null && mappingConfig instanceof MappingConfig) 
			return ((MappingConfig) mappingConfig).getMappingResult(key);
		else
		{
			
			return null;
	    }
    }

    
    
      // throw new IllegalStateException();
    

	private void loadConfig() {
	Map<ConfigType, String> filePaths = this.filePathMan.getFilePathsByEnvironment(this.environment);
	
	for(ConfigType key: filePaths.keySet()) {
		switch(key) {
			case NETWORK:
				//TODO
				break;
			case MAPPING:
				registerMappingConfig(filePaths.get(key));
				break;
			case DATABASE:
				//TODO
				break;
			default:
				break;
		}
	}
}

private void registerMappingConfig(String filePath) {
	Config mappingConfig = new MappingConfig(filePath, false);
	//registeredConfig.put(ConfigType.MAPPING, mappingConfig);
}
}

