package org.ucl.fhirwork.integration;

import cucumber.api.java.Before;
import org.ucl.fhirwork.integration.common.http.RestServerException;
import org.ucl.fhirwork.integration.cucumber.StepUtils;
import org.ucl.fhirwork.integration.ehr.EhrServer;
import org.ucl.fhirwork.integration.empi.EmpiServer;
import org.ucl.fhirwork.integration.empi.model.Person;
import org.ucl.fhirwork.integration.fhir.FhirServer;
import org.ucl.fhirwork.integration.fhir.FhirworkServer;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class IntegrationSteps
{
    private static boolean serversPinged = false;
    protected EhrServer ehrServer;
    protected EmpiServer empiServer;
    protected FhirServer fhirServer;
    protected FhirworkServer fhirworkServer;

    @Before
    public void setup() throws Exception
    {
        empiServer = new EmpiServer(
                System.getProperty("network.empi.address", "http://localhost:8080"),
                System.getProperty("network.empi.username", "admin"),
                System.getProperty("network.empi.password", "admin"));
        ehrServer = new EhrServer(
                System.getProperty("network.ehr.address", "http://localhost:8888/rest/v1"),
                System.getProperty("network.ehr.username", "guest"),
                System.getProperty("network.ehr.password", "guest"));
        fhirServer = new FhirServer(
                System.getProperty("network.fhir.address", "http://localhost:8090"));
        fhirworkServer = new FhirworkServer(
                System.getProperty("network.fhir.address", "http://localhost:8090"));

        if (! serversPinged) {
            StepUtils.wait(60, TimeUnit.SECONDS, () -> ehrServer.ping());
            StepUtils.wait(60, TimeUnit.SECONDS, () -> empiServer.ping());
            StepUtils.wait(60, TimeUnit.SECONDS, () -> fhirServer.ping());
            serversPinged = true;
        }
    }

    protected Person getPersonByName(String name) throws RestServerException
    {
        for (Person person: empiServer.getPeople()){
            if (Objects.equals(person.getGivenName(), name)){
                return person;
            }
        }
        throw new IllegalStateException();
    }
}
