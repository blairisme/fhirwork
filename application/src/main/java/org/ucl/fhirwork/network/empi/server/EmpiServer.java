/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.empi.server;

import static org.ucl.fhirwork.network.empi.server.EmpiEndpoint.AddPerson;
import static org.ucl.fhirwork.network.empi.server.EmpiEndpoint.Authenticate;
import static org.ucl.fhirwork.network.empi.server.EmpiHeader.SessionKey;
import static org.ucl.fhirwork.common.http.HttpHeader.ContentType;
import static org.ucl.fhirwork.common.http.MimeType.Xml;

import com.google.common.collect.ImmutableMap;
import org.ucl.fhirwork.common.http.RestException;
import org.ucl.fhirwork.common.http.RestServer;
import org.ucl.fhirwork.common.serialization.XmlSerializer;
import org.ucl.fhirwork.network.empi.data.AuthenticationRequest;
import org.ucl.fhirwork.network.empi.data.Person;

public class EmpiServer
{
    private RestServer server;
    private String username;
    private String password;
    private String address;

    public EmpiServer()
    {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Person addPerson(Person person) throws RestException
    {
        RestServer server = getServer();
        server.put(AddPerson, person, Person.class);

        return person; //TODO: Return result from REST call.
    }

    private RestServer getServer() throws RestException
    {
        if (server == null) {
            String token = getSessionToken();
            server = new RestServer(address, new XmlSerializer(), ImmutableMap.of(ContentType, Xml, SessionKey, token));
        }
        return server;
    }

    private String getSessionToken() throws RestException
    {
        AuthenticationRequest authentication = new AuthenticationRequest(username, password);
        RestServer server = new RestServer(address, new XmlSerializer(), ImmutableMap.of(ContentType, Xml));
        return server.put(Authenticate, authentication, AuthenticationRequest.class);
    }
}
