package fhirconverter.utilities;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;

import fhirconverter.converter.OpenEMPIConnector;
import fhirconverter.converter.PatientFHIR;
import fhirconverter.exceptions.IdNotObtainedException;
import fhirconverter.exceptions.OpenEMPISchemeNotMetException;
import fhirconverter.exceptions.ResourceNotFoundException;

//@Ignore //(Blair) Test broken when project inherited from previous team. TODO: Fix
public class PatientHelperTests {
	
	private static String RESOURCEFOLDER = "src/test/resources/";

	/*NHS exist*/
	@Test
	public void testNHSexist() throws Exception {
		testRetrieveNHS("patientNHS.xml");
	}

	/* NHS successfully retrieved from patient with single identifier */
	@Test
	public void testNHSWithSingleIdentifierExist() throws Exception {
		testRetrieveNHS("patientNHSWithSingleIdentifier.xml");
	}

	/*Patient exists in the correct format but without NHS - throw exception*/
	@Test(expected = IdNotObtainedException.class)
	public void testNHSnotExist() throws Exception {
		testRetrieveNHS("patientWithoutNHS.xml");
	}

	/*Patient exists but XML does not contain person tag - throw exception*/
	@Test(expected = OpenEMPISchemeNotMetException.class)
	public void testPersonTagMissing() throws Exception {
		testRetrieveNHS("patientWithoutPersonTag.xml");
	}

	/* Patient doesn't exist - exception */
	@Test(expected = ResourceNotFoundException.class)
	public void testPatientNotExist() throws Exception {
		OpenEMPIConnector mockCaller = Mockito.mock(OpenEMPIConnector.class);

		String recordID = "1200";

		Mockito.when(mockCaller.readPerson(recordID)).thenThrow(new ResourceNotFoundException("ok"));

		PatientHelper helper = new PatientHelper(mockCaller);
		helper.retrieveNHSbyId(recordID);
	}

	private void testRetrieveNHS(String filename) throws Exception {
		String xml = new String(Files.readAllBytes(Paths.get(RESOURCEFOLDER + filename)), "UTF-8");

		String recordID = "ID";

		OpenEMPIConnector mockCaller = Mockito.mock(OpenEMPIConnector.class);
		Mockito.when(mockCaller.readPerson(recordID)).thenReturn(xml);

		PatientHelper helper = new PatientHelper(mockCaller);
		String nhsIdentifier = helper.retrieveNHSbyId(recordID);
		assertTrue("NHS is correct: ", ("65498798126459873232897".equals(nhsIdentifier)));
	}
}
