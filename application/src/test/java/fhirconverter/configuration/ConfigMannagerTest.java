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
		
		String EHRaddress = configMannager.getNetworkConfig(NetworkConfigType.EHR, "address");
		String EHRusername = configMannager.getNetworkConfig(NetworkConfigType.EHR, "UserName");
		String EHRpassword = configMannager.getNetworkConfig(NetworkConfigType.EHR, "paSsWord");
		
		String EMPIaddress = configMannager.getNetworkConfig(NetworkConfigType.EMPI, "Address");
		String EMPIusername = configMannager.getNetworkConfig(NetworkConfigType.EMPI, "userName");
		String EMPIpassword = configMannager.getNetworkConfig(NetworkConfigType.EMPI, "PASSWORD");
	
		assertTrue("EHR address is not correct", EHRaddress.equals("https://test.operon.systems/rest/v1"));
		assertTrue("EHR username is not correct", EHRusername.equals("oprn_jarrod"));
		assertTrue("EHR password is not correct", EHRpassword.equals("ZayFYCiO644"));
		
		assertTrue("EMPI address is not correct", EMPIaddress.equals("http://localhost:8080"));
		assertTrue("EMPI username is not correct", EMPIusername.equals("admin"));
		assertTrue("EMPI password is not correct", EMPIpassword.equals("admin"));
	}

}
