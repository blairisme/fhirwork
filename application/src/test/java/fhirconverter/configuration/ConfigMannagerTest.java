package fhirconverter.configuration;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigMannagerTest {

	//TODO generate the testing files in this class
	
	@Test
	public void getMappingResultTest() {
		ConfigMannager configMannager = new ConfigMannager(Environment.TESTING);
		
		Object result = configMannager.getMappingResult("3141-9");
		assertTrue("The return value of getMappingResult() is not a String", result instanceof String);
		String resultInString = (String)result;
		assertEquals("The return value of getMappingResult() is not correct", "Weight", resultInString);
	}
	
	@Test
	public void getNetworkConfigTest() {
		ConfigMannager configMannager = new ConfigMannager(Environment.TESTING);
		
		String EHRaddress = configMannager.getNetworkAddress(NetworkConfigType.EHR);
		String EMPIusername = configMannager.getNetworkUsername(NetworkConfigType.EMPI);
		String EMPIpassword = configMannager.getNetworkPassword(NetworkConfigType.EMPI);
	
		assertTrue("EHR address is not correct", EHRaddress.equals("https://test.operon.systems/rest/v1"));
		assertTrue("EMPI username is not correct", EMPIusername.equals("admin"));
		assertTrue("EMPI password is not correct", EMPIpassword.equals("admin"));
	}

}
