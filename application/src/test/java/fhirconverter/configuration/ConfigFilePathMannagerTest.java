package fhirconverter.configuration;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigFilePathMannagerTest {

	@Test
	public void getFilePathTest() {
		ConfigFilePathMannager filePathMannger = new ConfigFilePathMannager();
		String output = filePathMannger.getFilePath(ConfigFilePathMannager.TESTING, "MappingConfig");
		assertTrue("path not correctly load", output.equals("src/main/resources/mappingConfig.json"));
	}

}
