package fhirconverter.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//in progress by Chenghu Fan
public class FilePathMannager {
	private Map<String, String>filePath = null;
	
	public FilePathMannager(){
		this.filePath = loadFilePath();
	}
	
	//TODO error checking
	public String getFilePath(String fileName){
		return filePath.get(fileName);
	}
	
	//TODO change the file format to json
	private Map<String, String> loadFilePath(){
		Map<String, String>inputFilePath = new HashMap<>();
		File file = new File("src/main/resources/filePath");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String pathLine = null;
            while ((pathLine = reader.readLine()) != null) {
            	String[] path = pathLine.split(" -- ");
            	if(path.length != 2)
            		System.out.println("An invalid config line detected in filePath");
            	else if(isSecurePath(path[0]))
            		System.out.println("The file path requested is not allowed to be accessed");
            	else{
            		inputFilePath.put(path[0], path[1]);
            	}
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                try {reader.close();} catch (Exception e) {}
        }
        return inputFilePath;
	}
	
	//possibly not have all insecure cases covered
	private boolean isSecurePath(String path){
		if(path.startsWith("/")){
			return false;
		}
		if(path.contains("..")){
			return false;
		}
		if(path.contains("~")){
			return false;
		}
		return true;
	}
}
