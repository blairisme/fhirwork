package fhirconverter.configuration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class gsonSerializer {
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
