package fhirconverter.configuration;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.junit.Test;

public class MappingConfigTests {
	private String testingFileFolder = "src/test/resources/";

	@Test
	public void configModificationTest() {
		generatingTestingFile();
		MappingConfig config = new MappingConfig(this.testingFileFolder + "mappingConfigTest.json", false);
		assertTrue("The content of the testing file is not as expected", config.getMappingResult("8302-2").toString().equals("Height"));
		
		config.addConfig("100-86", "Mobile");
		assertTrue("addConfig method does not work correctly", config.getMappingResult("100-86").toString().equals("Mobile"));
		
		config.changeConfig("100-86", "Testing value");
		assertTrue("changeConfig method does not work correctly", config.getMappingResult("100-86").toString().equals("Testing value"));
	
		config.removeConfig("100-86");
		assertTrue("removeConfig method does not work correctly", config.getMappingResult("100-86")==null);
	}
	
	private void generatingTestingFile() {
		String jsonString = "{\"3141-9\":\"Weight\",\"8302-2\":\"Height\",\"39156-5\":\"BMI\",\"8287-5\":\"Head circumference\",\"37362-1\":\"XR Bone age\"}";
        byte buffer[] = jsonString.getBytes();
        OutputStream out=null;
        try {
            out = new FileOutputStream(this.testingFileFolder + "mappingConfigTest.json");
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
