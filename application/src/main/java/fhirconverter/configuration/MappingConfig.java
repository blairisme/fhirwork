package fhirconverter.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class MappingConfig extends Config {
	
	public MappingConfig(String filePath){
		super("Mapping", filePath);
	}
	
	public static Map<String, String> getMappingConfig(){
		File file = new File(new FilePathMannager().getFilePath("mapping_config"));
		Map<String, String> codeMap = new HashMap<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String mappingConfigLine = null;
            while ((mappingConfigLine = reader.readLine()) != null) {
            	String[] mappingConfig = mappingConfigLine.split(",");
            	if(mappingConfig.length != 2)
            		System.out.println("An invalid config line detected in mapping_config, make sure each line only contains one comma");
            	else{
            		codeMap.put(mappingConfig[0], mappingConfig[1]);
            	}
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                try {reader.close();} catch (Exception e) {}
        }
		return codeMap;
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
