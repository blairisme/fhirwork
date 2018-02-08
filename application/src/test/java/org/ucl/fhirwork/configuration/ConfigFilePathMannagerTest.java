package org.ucl.fhirwork.configuration;

import static org.junit.Assert.*;

import org.junit.Test;
import org.ucl.fhirwork.configurationSystem.ConfigFilePathMannager;
import org.ucl.fhirwork.configurationSystem.ConfigType;
import org.ucl.fhirwork.configurationSystem.Environment;

public class ConfigFilePathMannagerTest {

	//TODO generate the testing files in this class
	
	@Test
	public void getFilePathTest() {
		ConfigFilePathMannager filePathMannger = new ConfigFilePathMannager(ConfigFilePathMannager.DEFAULT_PATH_FILE_LOCATION);
		String output = filePathMannger.getFilePathsByEnvironment(Environment.TESTING).get(ConfigType.MAPPING);
		assertTrue("path not correctly load", output.equals("src/main/resources/mappingConfig.json"));
	}
	
}
