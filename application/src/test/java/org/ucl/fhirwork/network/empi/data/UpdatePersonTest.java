package org.ucl.fhirwork.network.empi.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;

import javax.inject.Provider;

import org.junit.Test;
import org.ucl.fhirwork.common.http.RestException;
import org.ucl.fhirwork.common.http.RestServer;
import org.ucl.fhirwork.common.serialization.XmlSerializer;
import org.ucl.fhirwork.network.empi.server.EmpiServer;
import org.ucl.fhirwork.test.TestResourceUtils;

public class UpdatePersonTest {
	
	@Test
	public void updatePersonTest() throws RestException, IOException{
		XmlSerializer serializer = new XmlSerializer();
		String person = TestResourceUtils.readResource("empi/AddPersonExample.xml");
		String updatePerson = TestResourceUtils.readResource("empi/UpdatePersonExample.xml");
		Provider<RestServer> provider = new Provider<RestServer>() {
			@Override
			public RestServer get() {
				RestServer instance = new RestServer();
				return instance ;
			}
		};
		Person deserialized = serializer.deserialize(person, Person.class);
		Person updatedPerson = serializer.deserialize(updatePerson, Person.class);
		
		System.out.println(deserialized.getFamilyName()+" "+ deserialized.getGivenName() + " " + deserialized.getPersonId() );
		EmpiServer server = new EmpiServer(provider);
		server.setAddress("http://127.0.0.1:8080/");
		server.setUsername("admin");
		server.setPassword("admin");
		Person a = server.addPerson(deserialized);
		assertEquals("Anna", a.getGivenName());
		updatedPerson.setPersonId(a.getPersonId());
		
		System.out.println(a.getPersonId());
		Person b = server.updatePerson(updatedPerson);
//		server.removePerson(b.getPersonId());
		assertEquals("sb", b.getGivenName());
	}
}
