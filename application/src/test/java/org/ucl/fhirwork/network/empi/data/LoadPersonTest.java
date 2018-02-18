package org.ucl.fhirwork.network.empi.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.inject.Provider;

import org.junit.Test;
import org.ucl.fhirwork.common.http.RestException;
import org.ucl.fhirwork.common.http.RestServer;
import org.ucl.fhirwork.common.serialization.XmlSerializer;
import org.ucl.fhirwork.network.empi.server.EmpiServer;
import org.ucl.fhirwork.test.TestResourceUtils;

public class LoadPersonTest {
	@Test
	public void loadPersonTest() throws RestException, IOException{
		 XmlSerializer serializer = new XmlSerializer();
	     String person = TestResourceUtils.readResource("empi/AddPersonExample.xml");
	     Provider<RestServer> provider = new Provider<RestServer>() {
			@Override
			public RestServer get() {
				RestServer instance = new RestServer();
				return instance ;
			}
		};

        Person deserialized = serializer.deserialize(person, Person.class);
        System.out.println(deserialized.getFamilyName()+" "+ deserialized.getGivenName() + " " + deserialized.getPersonId() );
        EmpiServer server = new EmpiServer(provider);
        server.setAddress("http://127.0.0.1:8080/");
        server.setUsername("admin");
        server.setPassword("admin");
        Person re = server.addPerson(deserialized);
        Person load = server.loadPerson(re.getPersonId());
        	server.removePerson(re.getPersonId());
        assertEquals(deserialized.getFamilyName(), load.getFamilyName());
//          load = server.loadPerson(re.getPersonId());
//          assertNotNull(load);
        
	}
	@Test (expected = RestException.class)
	public void loadPersonNotExit() throws RestException{
		Provider<RestServer> provider = new Provider<RestServer>() {
			@Override
			public RestServer get() {
				RestServer instance = new RestServer();
				return instance ;
			}
		};
		 EmpiServer server = new EmpiServer(provider);
	    server.setAddress("http://127.0.0.1:8080/");
	    server.setUsername("admin");
	    server.setPassword("admin");
	    Person load = server.loadPerson("999");
	}
	
}
