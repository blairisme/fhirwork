/**
 * 
 */
package fhirconverter.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test Cases to test functionalities of the class OpenEMPIbase
 * 
 * @author Shruti Sinha
 *
 */
@Ignore //(Blair) Test broken when project inherited from previous team. TODO: Fix
public class OpenEMPIConnectorTests {

	private static final Logger logger = LogManager.getLogger(OpenEMPIConnectorTests.class); 
	
	public static String addPersonParameters = "<person><address1>8 Winterhouse</address1>"
			+ "<address2>Main Road</address2>"
			+ "<birthOrder>3</birthOrder>"
			+ "<birthPlace>Brighton</birthPlace>"
			+ "<city>London</city>"
			+ "<country>United Kingdom</country>"
			+ "<countryCode>UK</countryCode>"
			+ "<dateOfBirth>1932-10-19T00:00:00Z</dateOfBirth>"
			+ "<deathInd>23</deathInd>"
			+ "<deathTime>2017-03-19T12:00:00Z</deathTime>"
			+ "<degree>Masters</degree>"
			+ "<familyName>Mill</familyName>"
			+ "<fatherName>Pret</fatherName>"
			+ "<gender>"
			+ "<genderCd>1</genderCd>"
			+ "<genderCode>F</genderCode>"
			+ "<genderDescription>Female</genderDescription>"
			+ "<genderName>Female</genderName>"
			+ "</gender>"
			+ "<givenName>Anna</givenName>"
			+ "<maritalStatusCode>Married</maritalStatusCode>"
			+ "<middleName>Will</middleName>"
			+ "<motherName>Min</motherName>"
			+ "<mothersMaidenName>Kim</mothersMaidenName>"
			+ "<multipleBirthInd>2</multipleBirthInd>"
			+ "<personIdentifiers>"
			+ "<identifier>555</identifier>"
			+ "<identifierDomain>"
			+ "<identifierDomainId>10</identifierDomainId>"
			+ "<identifierDomainName>SSN</identifierDomainName>"
			+ "</identifierDomain>"
			+ "</personIdentifiers>"
			+ "<phoneAreaCode>44</phoneAreaCode>"
			+ "<phoneCountryCode>44</phoneCountryCode>"
			+ "<phoneExt>888</phoneExt>"
			+ "<phoneNumber>90909090</phoneNumber>"
			+ "<postalCode>SE1234L</postalCode>"
			+ "<prefix>Ms</prefix>"
			+ "<ssn>666</ssn>"
			+ "<state>London</state>"
			+ "<suffix>SS</suffix>"
			+ "<language>English</language>"
			+ "<email>anna@gmail.com</email>"
			+ "</person>";
	
	public static String addParametersWithNHS = "<person><address1>8 TowerHouse</address1>"
			+ "<address2>Main Road</address2>"
			+ "<birthOrder>3</birthOrder>"
			+ "<birthPlace>Ipswich</birthPlace>"
			+ "<city>London</city>"
			+ "<country>United Kingdom</country>"
			+ "<countryCode>UK</countryCode>"
			+ "<dateOfBirth>1982-10-19T00:00:00Z</dateOfBirth>"
			+ "<deathInd>23</deathInd>"
			+ "<degree>Masters</degree>"
			+ "<familyName>Priti</familyName>"
			+ "<fatherName>Tom</fatherName>"
			+ "<gender>"
			+ "	<genderCd>1</genderCd>"
			+ "	<genderCode>F</genderCode>"
			+ "	<genderDescription>Female</genderDescription>"
			+ "	<genderName>Female</genderName>"
			+ "</gender>"
			+ "<givenName>Gemma</givenName>"
			+ "<maritalStatusCode>Single</maritalStatusCode>"
			+ "<middleName>Hen</middleName>"
			+ "<motherName>Jolly</motherName>"
			+ "<mothersMaidenName>Lisa</mothersMaidenName>"
			+ "<multipleBirthInd>2</multipleBirthInd>"
			+ "<personIdentifiers>"
			+ "<identifier>555</identifier>"
			+ "<identifierDomain>"
			+ "<identifierDomainName>NHS</identifierDomainName>"
			+ "</identifierDomain>"
			+ "</personIdentifiers>"
			+ "<phoneAreaCode>44</phoneAreaCode>"
			+ "<phoneCountryCode>44</phoneCountryCode>"
			+ "<phoneExt>888</phoneExt>"
			+ "<phoneNumber>90909090</phoneNumber>"
			+ "<postalCode>SE1234L</postalCode>"
			+ "<prefix>Ms</prefix>"
			+ "<ssn>666</ssn>"
			+ "<state>London</state>"
			+ "<suffix>SS</suffix>"
			+ "<language>English</language>"
			+ "<email>gen@gmail.com</email>"
			+ "</person>";
	
	public static String updatePersonParameters = "<person>"
			+ "<birthPlace>Brighton</birthPlace>"
			+ "<city>London</city>"
			+ "<country>United Kingdom</country>"
			+ "<countryCode>UK</countryCode>"
			+ "<deathInd>23</deathInd>"
			+ "<familyName>Mill</familyName>"
			+ "<fatherName>Pret</fatherName>"
			+ "<gender>"
			+ "<genderCd>1</genderCd>"
			+ "<genderCode>F</genderCode>"
			+ "<genderDescription>Female</genderDescription>"
			+ "<genderName>Female</genderName>"
			+ "</gender>"
			+ "<givenName>Anna</givenName>"
			+ "<middleName>Penny</middleName>"
			+ "<motherName>Min</motherName>"
//			+ "<personIdentifiers>"
//			+ "<identifier>5000</identifier>"
//			+ "<identifierDomain>"
//			+ "<identifierDomainId>10</identifierDomainId>"
//			+ "<identifierDomainName>SSN</identifierDomainName>"
//			+ "</identifierDomain>"
//			+ "</personIdentifiers>"
			+ "<phoneAreaCode>44</phoneAreaCode>"
			+ "<phoneCountryCode>44</phoneCountryCode>"
			+ "<phoneExt>888</phoneExt>"
			+ "<phoneNumber>90909090</phoneNumber>"
			+ "<postalCode>SE1234L</postalCode>"
			+ "<prefix>Ms</prefix>"
			+ "<state>London</state>"
			+ "<language>English</language>"
			+ "</person>";
	
	public static String sampleParameter = "<person><birthPlace>Brighton</birthPlace><city>London</city>"
			+ "<country>United Kingdom</country><countryCode>UK</countryCode><familyName>Mill</familyName>"
			+ "<fatherName>Pret</fatherName><givenName>Anna</givenName><personId>0</personId>"
			+ "<personIdentifiers><dateCreated>2017-06-30T12:32:32.899Z</dateCreated>"
			+ "<identifier>309a45a0-5d90-11e7-975b-0242ac1a0003</identifier><identifierDomain>"
			+ "<identifierDomainId>18</identifierDomainId>"
			+ "<identifierDomainName>OpenEMPI</identifierDomainName>"
			+ "<namespaceIdentifier>2.16.840.1.113883.4.357</namespaceIdentifier>"
			+ "<universalIdentifier>2.16.840.1.113883.4.357</universalIdentifier>"
			+ "<universalIdentifierTypeCode>hl7</universalIdentifierTypeCode></identifierDomain>"
			+ "<personIdentifierId>1</personIdentifierId></personIdentifiers>"
			+ "<personIdentifiers><dateCreated>2017-06-30T12:32:32.899Z</dateCreated>"
			+ "<identifier>555</identifier><identifierDomain>"
			+ "<identifierDomainId>10</identifierDomainId>"
			+ "<identifierDomainName>SSN</identifierDomainName>"
			+ "<namespaceIdentifier>2.16.840.1.113883.4.1</namespaceIdentifier>"
			+ "<universalIdentifier>2.16.840.1.113883.4.1</universalIdentifier>"
			+ "<universalIdentifierTypeCode>SSN</universalIdentifierTypeCode></identifierDomain>"
			+ "<personIdentifierId>0</personIdentifierId></personIdentifiers>"
			+ "<personIdentifiers><dateCreated>2017-07-05T16:17:52.672Z</dateCreated>"
			+ "<identifier>111</identifier><identifierDomain>"
			+ "<identifierDomainId>11</identifierDomainId><identifierDomainName>TIN</identifierDomainName>"
			+ "<namespaceIdentifier>2.16.840.1.113883.4.2</namespaceIdentifier>"
			+ "<universalIdentifier>2.16.840.1.113883.4.2</universalIdentifier>"
			+ "<universalIdentifierTypeCode>TIN</universalIdentifierTypeCode></identifierDomain>"
			+ "<personIdentifierId>10</personIdentifierId></personIdentifiers>"			
			+ "</person>";
	
	public static String expRemoveOpenEMPI = "<person><birthPlace>Brighton</birthPlace><country>United Kingdom</country>"
			+ "<fatherName>Pret</fatherName><city>London</city><countryCode>UK</countryCode><familyName>Mill</familyName>"
			+ "<givenName>Anna</givenName><personId>0</personId>"
			+ "<personIdentifiers>"
			+ "<identifierDomain>"
			+ "<universalIdentifierTypeCode>SSN</universalIdentifierTypeCode>"
			+ "<namespaceIdentifier>2.16.840.1.113883.4.1</namespaceIdentifier>"
			+ "<universalIdentifier>2.16.840.1.113883.4.1</universalIdentifier>"
			+ "<identifierDomainId>10</identifierDomainId>"
			+ "<identifierDomainName>SSN</identifierDomainName>"
			+ "</identifierDomain>"
			+ "<identifier>555</identifier>"
			+ "<dateCreated>2017-06-30T12:32:32.899Z</dateCreated>"
			+ "<personIdentifierId>0</personIdentifierId>"
			+ "</personIdentifiers>"
			+ "<personIdentifiers>"
			+ "<identifierDomain>"
			+ "<universalIdentifierTypeCode>TIN</universalIdentifierTypeCode>"
			+ "<namespaceIdentifier>2.16.840.1.113883.4.2</namespaceIdentifier>"
			+ "<universalIdentifier>2.16.840.1.113883.4.2</universalIdentifier>"
			+ "<identifierDomainId>11</identifierDomainId>"
			+ "<identifierDomainName>TIN</identifierDomainName>"
			+ "</identifierDomain>"
			+ "<identifier>111</identifier>"
			+ "<dateCreated>2017-07-05T16:17:52.672Z</dateCreated>"
			+ "<personIdentifierId>10</personIdentifierId>"
			+ "</personIdentifiers>"	
			+ "</person>";
	
	/**
	 * Test for addPerson()
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddPerson() throws Exception {
		String personIdSSN = null;
		OpenEMPIConnector openEMPIconnector = new OpenEMPIConnector();
		String obtainedResults = openEMPIconnector.addPerson(addPersonParameters);
		if (!obtainedResults.isEmpty()) {
			personIdSSN = obtainedResults.substring(obtainedResults.indexOf("<personId>") + 10,
					obtainedResults.indexOf("</personId>"));
		}
		logger.info("*** testAddPerson ***");
		openEMPIconnector.removePersonById(personIdSSN);
		assertNotNull(personIdSSN);
	}

	/**
	 * Test for SearchPersonByAttributes()
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testSearchPersonByAttributes() throws Exception {
		String personId = "";
		OpenEMPIConnector openEMPIconnector = new OpenEMPIConnector();
		String obtainedResultsAdd = openEMPIconnector.addPerson(addPersonParameters);
		String test = "{ \"family\" : \"Mill\"}";
		if (!obtainedResultsAdd.isEmpty()) {
			personId = obtainedResultsAdd.substring(obtainedResultsAdd.indexOf("<personId>") + 10,
					obtainedResultsAdd.indexOf("</personId>"));
		}
		JSONObject searchParameters = new JSONObject(test);
		String obtainedResults = openEMPIconnector.searchPersonByAttributes(searchParameters);
		logger.info("*** testSearchPersonByAttributes ***");
		openEMPIconnector.removePersonById(personId);
		assertNotNull(obtainedResults);
	}

	/**
	 * Test for SearchPersonById() with duplicate records
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testSearchPersonById() throws Exception {
		String personId = "";
		OpenEMPIConnector openEMPIconnector = new OpenEMPIConnector();
		String obtainedResultsAdd = openEMPIconnector.addPerson(addPersonParameters);
		if (!obtainedResultsAdd.isEmpty()) {
			personId = obtainedResultsAdd.substring(obtainedResultsAdd.indexOf("<personId>") + 10,
					obtainedResultsAdd.indexOf("</personId>"));
		}
		String test = "{ \"identifier_value\" : \"555\", \"identifier_domain\" : \"NHS\"}";
		JSONObject serachWithIdParameters = new JSONObject(test);
		String obtainedResults = openEMPIconnector.searchPersonById(serachWithIdParameters);
		logger.info("*** testSearchPersonById ***");
		openEMPIconnector.removePersonById(personId);
		assertNotNull(obtainedResults);

	}

	/**
	 * Test for ReadPerson() with person Id
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testReadPerson() throws Exception {
		String personId = null;
		OpenEMPIConnector openEMPIconnector = new OpenEMPIConnector();
		String obtainedResultsAdd = openEMPIconnector.addPerson(addPersonParameters);
		if (!obtainedResultsAdd.isEmpty()) {
			personId = obtainedResultsAdd.substring(obtainedResultsAdd.indexOf("<personId>") + 10,
					obtainedResultsAdd.indexOf("</personId>"));
		}
		String obtainedResults = openEMPIconnector.readPerson(personId);
		openEMPIconnector.removePersonById(personId);
		logger.info("*** testAddPersonWithNHS ***");
		assertNotNull(obtainedResults);

	}

	/**
	 * Test for UpdatePerson()
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testUpdatePerson() throws Exception {
		String personId = "";
		OpenEMPIConnector openEMPIconnector = new OpenEMPIConnector();
		String obtainedResultsAdd = openEMPIconnector.addPerson(updatePersonParameters);
		if (!obtainedResultsAdd.isEmpty()) {
			personId = obtainedResultsAdd.substring(obtainedResultsAdd.indexOf("<personId>") + 10,
					obtainedResultsAdd.indexOf("</personId>"));
		}
		obtainedResultsAdd = obtainedResultsAdd.replace("Mill", "George");
		String obtainedResults = openEMPIconnector.updatePerson(obtainedResultsAdd);
		String expectedUpdatePersonResults = "Updated";
		openEMPIconnector.removePersonById(personId);
		logger.info("*** testUpdatePerson ***");
		assertEquals(expectedUpdatePersonResults, obtainedResults);

	}

	/**
	 * Test for RemovePersonById(). This test will first check if a person
	 * record exist to be removed
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testRemovePersonById() throws Exception {
		String personId = null;
		OpenEMPIConnector openEMPIconnector = new OpenEMPIConnector();
		String obtainedResultsAdd = openEMPIconnector.addPerson(addPersonParameters);
		if (!obtainedResultsAdd.isEmpty()) {
			personId = obtainedResultsAdd.substring(obtainedResultsAdd.indexOf("<personId>") + 10,
					obtainedResultsAdd.indexOf("</personId>"));
		}
		String removeParameters = personId;
		String expectedRemovePersonByIdResults = "Remove Successful";
		String obtainedResults = openEMPIconnector.removePersonById(removeParameters);
		logger.info("*** testRemovePersonById ***");
		assertEquals(expectedRemovePersonByIdResults, obtainedResults);
	}
	
	/**
	 * Test for removeOpenEMPIIdentifier()
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRemoveOpenEMPIIdentifier() throws Exception {
		OpenEMPIConnector openEMPIconnector = new OpenEMPIConnector();
		String expected = expRemoveOpenEMPI;
		String obtained = openEMPIconnector.removeOpenEMPIIdentifier(sampleParameter);
		logger.info("*** testRemoveOpenEMPIIdentifier ***");
		assertEquals(expected, obtained);
	}

	/**
	 * Test for fetchIdDomainsInRequest()
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFetchIdDomainsInRequest() throws Exception {
		OpenEMPIConnector openEMPIconnector = new OpenEMPIConnector();
		List<String> expected = new ArrayList<>(Arrays.asList("SSN", "TIN"));
		List<String> obtained = openEMPIconnector.fetchIdDomainsInRequest(sampleParameter);
		logger.info("*** testFetchIdDomainsInRequest ***");
		assertEquals(expected, obtained);
	}
}