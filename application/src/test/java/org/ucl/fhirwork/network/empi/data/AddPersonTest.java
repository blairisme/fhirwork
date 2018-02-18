package org.ucl.fhirwork.network.empi.data;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.inject.Provider;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.common.http.RestException;
import org.ucl.fhirwork.common.http.RestServer;
import org.ucl.fhirwork.common.serialization.XmlSerializer;
import org.ucl.fhirwork.network.empi.server.EmpiServer;
import org.ucl.fhirwork.test.TestResourceUtils;


public class AddPersonTest {


    @Test
    public void addPersonTest() throws IOException, RestException {
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
        Person a = server.addPerson(deserialized);
        System.out.println(a.getFamilyName()+" "+ a.getGivenName() + " " + a.getPersonId() );
	    server.removePerson(a.getPersonId());
        Assert.assertNotNull(a);




    }

}