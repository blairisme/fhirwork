package fhirconverter.converter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import fhirconverter.exceptions.IdNotObtainedException;

@Ignore //(Blair) Test broken when project inherited from previous team. TODO: Fix
public class OpenEHRConnectorTests{
	@Test
	public void getEHRidNumberTest() throws Exception{
		OpenEHRConnector tester = new OpenEHRConnector("openEhrApi");		
		assertEquals("Get EHRid operation","c831fe4d-0ce9-4a63-8bfa-2c51007f97e5",tester.getEHRIdByNhsNumber("9999999332"));
		tester.deleteSessionKey();

	}
	@Test(expected = IdNotObtainedException.class)
	public void getEHRidNumberNotExistTest() throws Exception{	
		OpenEHRConnector tester = new OpenEHRConnector("openEhrApi");	
		tester.getEHRIdByNhsNumber("999999933");
		tester.deleteSessionKey();
	}
	@Test
	public void getObservationsTests() throws Exception{
		OpenEHRConnector tester = new OpenEHRConnector("openEhrApi");	
		JSONObject responseObj = tester.getGrowthChartObservations("9999999332");
		if(responseObj.has("resultSet"))
		{
			assertNotNull(responseObj.getJSONArray("resultSet"));
		}else{
			assertEquals("Test Case Failed! No Response","1","2");
		}
		tester.deleteSessionKey();
		
	}
//	@Test
//	public void getObservationsTestsEthercis() throws Exception{
//		OpenEHRConnector tester = new OpenEHRConnector("ethercis");	
//		JSONObject responseObj = tester.getGrowthChartObservations("9999999000");
//		if(responseObj.has("resultSet"))
//		{
//			assertNotNull(responseObj.getJSONArray("resultSet"));
//		}else{
//			assertEquals("Test Case Failed! No Response","1","2");
//		}
//		tester.deleteSessionKey();
//	
//
//	}
//	@Test
//	public void getEHRidNumberByEthercis() throws Exception{
//		OpenEHRConnector tester = new OpenEHRConnector("ethercis");	
//		tester.getEHRIdByNhsNumber("9999999000");
//		tester.deleteSessionKey();
//	}
}



