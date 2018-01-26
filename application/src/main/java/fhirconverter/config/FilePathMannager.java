package fhirconverter.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FilePathMannager {
	private boolean autoTidyUpPathFile = false;
	private Map<String, String>filePath = null;
	
	public FilePathMannager(boolean autoTidyUpPathFile){
		this.autoTidyUpPathFile = autoTidyUpPathFile;
		if(this.autoTidyUpPathFile)
			tidyUpPathFile();
		this.filePath = loadFilePath();
	}
	
	public String getFilePath(String fileName){
		return filePath.get(fileName);
	}
	
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
	
	private void tidyUpPathFile(){
		//TODO
		
//		Map<String, String> inputFilePath = new HashMap<>();
//		if(this.filePath == null)
//			inputFilePath = loadFilePath();
//		else
//			inputFilePath = this.filePath;
//		
//		Comparator<Entry<String, String>> c = new Comparator<Entry<String, String>>() {  
//			@Override
//			public int compare(Entry<String, String> entry1, Entry<String, String> entry2) {
//				return entry1.getKey().compareToIgnoreCase(entry2.getKey());
//			}
//        };
//        
//        Collections.sort(list, new Comparator<Dog>()
	}
	
	public void addFilePathToPathFile(String fileName, String filePath){
		//TODO
	}
	
	public void removeFilePathFromPathFile(String fileName, String filePath){
		//TODO
	}
	
	public void changeFilePathInPathFile(String fileName, String filePath){
		//TODO
	}
	
	//get the file path of the specified file, only load path file when called
	public static String quickGetFilePath(String fileName){
		File file = new File("src/main/resources/filePath");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String pathLine = null;
            while ((pathLine = reader.readLine()) != null) {
            	String[] path = pathLine.split(" -- ");
            	if(path.length != 2)
            		System.out.println("An invalid config line detected in filePath");
            	else{
            		if(path[0].equals(fileName)){
            			return path[1];
            		}
            	}
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                try {reader.close();} catch (Exception e) {}
        }
		return null;
	}
}
