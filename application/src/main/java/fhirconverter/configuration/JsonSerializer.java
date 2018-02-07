package fhirconverter.configuration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

//TODO: have a list of errors/exceptions that this class should handle

public class JsonSerializer {
	
	/**This method implements a gson serializer for converting a JSON file to a specified java 
	 * type that can be supported by gson.
	 * 
	 * @param type - a java type that can be supported by gson fromJson() method
	 * @param filePath - the location of the source JSON file in the file system
	 * @return Object - the converted object in the format of a user-defined java type <br/>
	 * 		The method returns null if the conversion failed.
	 * */
	public Object fromJsonFileToObj(Type type, String filePath) {
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create(); 
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
		} catch (FileNotFoundException e) {
			
			System.out.println("The requsted file cannot be located, please try again");
			// TODO Auto-generated catch block
			br = null;
			
			e.printStackTrace();
		}
		if(br == null)
			return null;
		Object convertedObj = gson.fromJson(br, type);
        return convertedObj;
	}
	
	/**This method implements a gson serializer for converting a java object in a specified
	 * type that can be supported by gson to a JSON String and write it into a file.
	 * 
	 * @param type - a java type that can be supported by gson toJson() method
	 * @param obj - the java object to be converted
	 * @param filePath - the location of the source JSON file in the file system
	 * */
	public void fromObjToJsonFile(Type type, Object obj, String filePath) {
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create(); 
		String jsonString = gson.toJson(obj, type);
        byte buffer[] = jsonString.getBytes();
        OutputStream out=null;
        try {
            out = new FileOutputStream(filePath);
            out.write(buffer, 0, buffer.length);
        } catch (Exception e) {
            System.out.println(e.toString());
        }finally{
            try {
                out.close();
            } catch (IOException ioException) {
                System.out.println(ioException.toString());
            }
        }   
	}
}
