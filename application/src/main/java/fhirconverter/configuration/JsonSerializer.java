package fhirconverter.configuration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This class implements a gson serializer handling the serialization between JSON file and 
 * a specified java type that can be supported by gson.
 *
 * @author Abdul-qadir Ali
 * @author Chenghui Fan
 */

public class JsonSerializer {
	
	/**This method implements a gson serializer for converting a JSON file to a specified java 
	 * type that can be supported by gson.
	 * 
	 * @param Type - a java type that can be supported by gson fromJson() method
	 * @param File Path - the location of the source JSON file in the file system
	 * @return Object - the converted object in the format of a user-defined java type <br/>
	 * 		The method returns null if the conversion failed.
	 * */
	public Object fromJsonFileToSpecifiedTypeObj(Type type, String filePath) {
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create(); 
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			br = null;
			e.printStackTrace();
		}
		if(br == null)
			return null;
		Object convertedObj = gson.fromJson(br, type);
        return convertedObj;
	}
}
