package fhirconverter.configuration;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigMannagerTest {

	@Test
	public void getMappingResultTest() {
		ConfigMannager configMannager = new ConfigMannager(Environment.TESTING);
		
		Object result = configMannager.getMappingResult("3141-9");
		assertTrue("The return value of getMappingResult() is not a String", result instanceof String);
		String resultInString = (String)result;
		assertEquals("The return value of getMappingResult() is not correct", resultInString, "Weight");
	}

}
