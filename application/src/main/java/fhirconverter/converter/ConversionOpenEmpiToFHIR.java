/**
 * ConversionOpenEMPI_to_FHIR
 *
 * v2.0
 *
 * Date: 13-7-2017
 *
 * Copyrights: Koon Wei Teo, Evanthia Tingiri, Shruti Sinha
 *
 * Description: This class contains the necessary functions to convert OpenEMPI to FHIR.
 * 				It is called by PatientFHIR in the case of the following requests:
 * 				Read & Search
 *
 */

package fhirconverter.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.uhn.fhir.model.dstu2.composite.AddressDt;
import ca.uhn.fhir.model.dstu2.composite.ContactPointDt;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import ca.uhn.fhir.model.primitive.DateDt;
import ca.uhn.fhir.model.dstu2.composite.HumanNameDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.dstu2.resource.Bundle;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.primitive.PositiveIntDt;
import ca.uhn.fhir.model.primitive.InstantDt;
import ca.uhn.fhir.model.dstu2.valueset.ContactPointSystemEnum;
import ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum;
import ca.uhn.fhir.model.dstu2.valueset.NameUseEnum;
import ca.uhn.fhir.model.api.ResourceMetadataKeyEnum;
import ca.uhn.fhir.model.dstu2.valueset.MaritalStatusCodesEnum;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

public class ConversionOpenEmpiToFHIR {
	private Logger LOGGER = LogManager.getLogger(ConversionOpenEmpiToFHIR.class);

	protected List<Patient> conversion(String result){
		/* converts to JSONObject HashMap */
		JSONObject xmlJSONObj = XML.toJSONObject(result); 
		List<Patient> patients = new ArrayList<Patient>();
		if(xmlJSONObj.has("people")) {

            LOGGER.debug("optJSONArray: " + xmlJSONObj.optJSONArray("people"));
            LOGGER.debug("optJSONObj: " + xmlJSONObj.optJSONObject("people"));

            JSONObject people = xmlJSONObj.optJSONObject("people");

            if (people != null) {

                JSONObject person = people.optJSONObject("person");
                JSONArray personArray = people.optJSONArray("person");
                if(person != null) {
                    LOGGER.debug("Only One Person: " + person);
                    patients.add(personMapping(person));
                }
                else if (personArray != null)
                {
                    for (int i = 0; i < personArray.length(); i++) {
                        JSONObject personData = personArray.getJSONObject(i);
                        LOGGER.debug(i + "th Person: " + personData);
                        patients.add(personMapping(personData));
                    }
                }
            }
        }

        //  Read
		if(xmlJSONObj.has("person")){
			JSONObject person = xmlJSONObj.getJSONObject("person");

			patients.add(personMapping(person));
		}
		return patients;
	}

	protected Bundle setBundle(Bundle bundle, Patient patient){
		bundle.addEntry().setResource(patient);
		/* Extend if needed Koon
			.getRequest()
      				.setUrl("Patient")
      				.setIfNoneExist("identifier=http://acme.org/mrns|12345"); */
		return bundle;
	}
	protected Patient personMapping(JSONObject node)  { //tried automated using generic objects methods, but inner classes methods differ too much. Unable to get it more automated. By koon.
		Patient p = new Patient();
		
		/* Full Name */
		p.addName(setHumanName(node));
		
		/* Maiden Name */
		p = setMaidenName(p, node);

		System.out.println("Current patient's name: " + p.getName().get(0).getNameAsSingleString());
	    
		/* Primary Key */
		if(node.has("personId")){
			IdDt id = new IdDt(node.optString("personId"));
			p.setId(id);
		}	
		
		/* Date Changed */
		if(node.has("dateChanged")) {
			p.getResourceMetadata().put(ResourceMetadataKeyEnum.UPDATED,  new InstantDt(convertStringtoDate(node.optString("dateChanged"))));
		}
		
		/* Marital Status */
		p = setMaritalStatus(p, node);
		
			
		/* Address */
		if(setAddress(node) !=null) {
			p.addAddress(setAddress(node));
			System.out.println("Patient's address: " + p.getAddress().get(0).getText());
		}
		
		/* -- Contact Details -- */
		p = setContactDatails(p,node);
		
		/* -- SET THE BIRTH DETAILS OF THE PATIENT -- */
		p = setBirthDetails(p, node);

		/* Person Identifiers */
		if(node.has("personIdentifiers")){
			p = setPersonIdentifiers(node,p);
		}
		
		/* Death Date & Time */
		if(node.has("deathTime")){
			DateTimeDt deceasedDate = new DateTimeDt(node.optString("deathTime"));
			p.setDeceased(deceasedDate);
		}
		
		return p;
	}

	private Patient setMaidenName(Patient p, JSONObject node) {
		Patient temp = p;
		if(node.has("mothersMaidenName"))
		{
			HumanNameDt maidenName = new HumanNameDt();
			maidenName.addFamily(node.optString("mothersMaidenName"));
			maidenName.setUse(NameUseEnum.MAIDEN);
			temp.addName(maidenName);
		}
		
		return temp;
	}
	
	private Patient setMaritalStatus(Patient p, JSONObject node) {
		Patient temp = p;
		
		if(node.has("maritalStatusCode")) {
			String martialStatus = node.getString("maritalStatusCode").toUpperCase();
			if("MARRIED".equals(martialStatus)){
				temp.setMaritalStatus(MaritalStatusCodesEnum.M);
			}else if("ANNULLED".equals(martialStatus)){
				temp.setMaritalStatus(MaritalStatusCodesEnum.A);
			}else if("DIVORCED".equals(martialStatus)){
				temp.setMaritalStatus(MaritalStatusCodesEnum.D);
			}else if("INTERLOCUTORY".equals(martialStatus)){
				temp.setMaritalStatus(MaritalStatusCodesEnum.I);
			}else if("LEGALLY SEPARATED".equals(martialStatus)){
				temp.setMaritalStatus(MaritalStatusCodesEnum.L);
			}else if("POLYGAMOUS".equals(martialStatus)){
				temp.setMaritalStatus(MaritalStatusCodesEnum.P);
			}else if("NEVER MARRIED".equals(martialStatus)){
				temp.setMaritalStatus(MaritalStatusCodesEnum.S);
			}else if("DOMESTIC PARTNER".equals(martialStatus)){
				temp.setMaritalStatus(MaritalStatusCodesEnum.T);
			}else if("WIDOWED".equals(martialStatus)){
				temp.setMaritalStatus(MaritalStatusCodesEnum.W);
			}else{
				temp.setMaritalStatus(MaritalStatusCodesEnum.UNK);
			}
		}
		
		return temp;
	}
	
	private Patient setContactDatails(Patient p, JSONObject node) {
		Patient temp = p;
		ArrayList<ContactPointDt> telecom = new ArrayList<ContactPointDt>();

		/*email*/
		if(node.has("email")) {
			ContactPointDt email = new ContactPointDt();
			email.setSystem(ContactPointSystemEnum.EMAIL).setValue(node.getString("email"));
			telecom.add(email);
		}
		
		/*Phone*/
		ContactPointDt phone = new ContactPointDt();
		phone = setPhone(node);
		/* REMEMBER TO CHECK IT */
		if(phone.getValue()!=null)
			telecom.add(phone);
		if(telecom.size()>0) {
			temp.setTelecom(telecom);
			for(int z=0; z<telecom.size(); z++)
				System.out.println("Contact: " + temp.getTelecom().get(z).getValue());
		}
		return temp;
	}
	
	private Patient setBirthDetails(Patient p, JSONObject node) {
		Patient temp = p;
		if(node.has("birthOrder")) {
			PositiveIntDt birthOrder = new PositiveIntDt(node.optInt("birthOrder"));
			temp.setMultipleBirth(birthOrder);
		}
		if(node.has("dateOfBirth")){
			String birthDateString = node.optString("dateOfBirth").substring(0,10);
			temp.setBirthDate(new DateDt(birthDateString));
		}
		if(node.has("gender")){
			JSONObject genders = node.getJSONObject("gender");
			if(genders.has("genderDescription")) {
				String gender = genders.optString("genderDescription");
				gender = gender.toUpperCase();
				temp.setGender(AdministrativeGenderEnum.valueOf(gender));
			}
		}
		return temp;
	}
	
	/* It is used to construct the text attribute of AddressDt */
	private String checkText(AddressDt t) {
		if(t.getText()==null) {
			return "";
		}
		else {
			return t.getText();
		}
	}

	/* It is used to construct the Phone Number */
	private String checkPhone(ContactPointDt p, boolean exists) {
		if (exists)
			return p.getValue();
		else
			return "";
	}


	/* Create a FHIR object that contains the Full Name details */
	protected HumanNameDt setHumanName(JSONObject node){
		/* Full Name */
		HumanNameDt n = new HumanNameDt();
		
		/*Given Name*/
		if(node.has("givenName")) {
			n.addGiven(node.optString("givenName"));
		}				
		
		/*Family Name*/
		if(node.has("familyName")) {
			n.addFamily(node.optString("familyName"));
			n.setUse(NameUseEnum.USUAL);
		}	
		
		/*Middle Name*/
		if(node.has("middleName")) {
			n.addGiven(node.optString("middleName"));
		}
		
		/*Prefix*/
		if(node.has("prefix")) {
			n.addPrefix(node.optString("prefix"));
		}
		
		/*Suffix*/
		if(node.has("suffix")) {
			n.addSuffix(node.optString("suffix"));
		}
		return n;
	}


	/* Create a FHIR object that contains the address details */
	protected AddressDt setAddress(JSONObject node){
		AddressDt t = new AddressDt();
		
		/*Address1*/
		if(node.has("address1")) {
			t.addLine(node.optString("address1"));
			t.setText(node.optString("address1") + " ");
		}
		
		/*Address2*/
		if(node.has("address2")) {
			t.addLine(node.optString("address2"));
			t.setText(checkText(t) + node.optString("address2") + " ");
		}
		
		/*City*/
		if(node.has("city")) {
			t.setCity(node.optString("city"));
			t.setText(checkText(t) + node.optString("city")+ " ");

		}
		
		/*State*/
		if(node.has("state")) {
			t.setState(node.optString("state"));
			t.setText(checkText(t) + node.optString("state") + " ");
		}
		
		/*Postal Code*/
		if(node.has("postalCode")) {
			t.setPostalCode(node.optString("postalCode"));
			t.setText(checkText(t) + node.optString("postalCode")+ " ");

		}
		
		/*Country*/
		if(node.has("country")) {
			t.setCountry(node.optString("country"));
			t.setText(checkText(t) + node.optString("country"));

		}
		return t;

	}

	protected ContactPointDt setPhone(JSONObject node){
		ContactPointDt phone = new ContactPointDt();
		phone.setSystem(ContactPointSystemEnum.PHONE);
		boolean exists = false;

		//phone country code
		if(node.has("phoneCountryCode")) {
			exists = true;
			phone.setValue(node.optString("phoneCountryCode"));
		}

		//phone area code
		if(node.has("phoneAreaCode")) {
			phone.setValue(checkPhone(phone, exists) + node.optString("phoneAreaCode"));
			exists = true;
		}

		//phone number
		if(node.has("phoneNumber")) {
			phone.setValue(checkPhone(phone, exists) + node.optString("phoneNumber"));
			exists = true;
		}

		//phone extension
		if(node.has("phoneExt")) {
			phone.setValue(checkPhone(phone, exists) + node.optString("phoneExt"));
			exists = true;
		}


		return phone;
	}

	protected Patient setPersonIdentifiers(JSONObject node, Patient p) {
		JSONArray personIdentifiers = node.optJSONArray("personIdentifiers");

		if(personIdentifiers==null) {
			IdentifierDt identifier = new IdentifierDt();
			System.out.println("person identifiers not an array");
			JSONObject id = node.getJSONObject("personIdentifiers");

			if(id.has("identifier")) {
				identifier.setValue(id.optString("identifier"));
			}
			if(id.has("identifierDomain")) {
				JSONObject domain = id.getJSONObject("identifierDomain");

				if(domain.has("namespaceIdentifier")) {
					identifier.setSystem(domain.optString("identifierDomainName"));
				}

			}
			p.addIdentifier(identifier);
		}
		else {
			JSONArray ids = node.getJSONArray("personIdentifiers");
			for(int i=0; i<ids.length(); i++) {
				IdentifierDt identifier = new IdentifierDt();
				JSONObject id = ids.getJSONObject(i);

				if(id.has("identifier")) {
					identifier.setValue(id.optString( "identifier"));
				}

				if(id.has("identifierDomain")) {
					JSONObject domain = id.getJSONObject("identifierDomain");
					if((domain.has("identifierDomainName"))&&(!"OpenEMPI".equals(domain.optString("identifierDomainName")))) {
						identifier.setSystem(domain.optString("identifierDomainName"));
					}
					else
						continue;
				}
				p.addIdentifier(identifier);

			}
		}

		return p;
	}

	/**
	 * This method converts date in string format to date format and removes the time/ 
	 * @param date
	 * @return dateTransform
	 */
	protected Date convertStringtoDate(String date){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		date = date.substring(0, date.length() - 8);
		Date dateTransform = null;
		try{
			dateTransform = formatter.parse(date);
			//SimpleDateFormat fhirFormat = new SimpleDateFormat("yyyy-MM-dd");
		} catch (ParseException e){
			e.printStackTrace();
		}
		return dateTransform;
	}
}
