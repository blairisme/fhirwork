package org.ucl.fhirwork.integration;

import cucumber.api.java.Before;
import org.ucl.fhirwork.integration.common.http.HttpUtils;
import org.ucl.fhirwork.integration.common.http.RestServerException;
import org.ucl.fhirwork.integration.cucumber.StepUtils;
import org.ucl.fhirwork.integration.ehr.EhrServer;
import org.ucl.fhirwork.integration.empi.EmpiServer;
import org.ucl.fhirwork.integration.empi.model.Person;
import org.ucl.fhirwork.integration.fhir.FhirServer;
import org.ucl.fhirwork.integration.fhir.FhirworkServer;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class IntegrationSteps
{
    private static EhrServer ehrServer;
    private static EmpiServer empiServer;
    private static FhirServer fhirServer;
    private static FhirworkServer fhirworkServer;

    @Before
    public void setup() throws TimeoutException {
        initializeEhrServer();
        initializeEmpiServer();
        initializeFhirServer();
    }

    protected synchronized EhrServer getEhrServer() {
        return ehrServer;
    }

    protected synchronized EmpiServer getEmpiServer(){
        return empiServer;
    }

    protected synchronized FhirServer getFhirServer() {
        return fhirServer;
    }

    protected synchronized FhirworkServer getFhirworkServer() {
        return fhirworkServer;
    }

    private void initializeEmpiServer() throws TimeoutException {
        if (empiServer == null) {
            empiServer = new EmpiServer(
                System.getProperty("network.empi.address", "http://localhost:8080"),
                System.getProperty("network.empi.username", "admin"),
                System.getProperty("network.empi.password", "admin"));

            StepUtils.wait(120, TimeUnit.SECONDS, () ->
                HttpUtils.pingUrl(empiServer.getPingAddress()), empiServer.getAddress());
        }
    }

    private void initializeEhrServer() throws TimeoutException {
        if (ehrServer == null) {
            ehrServer = new EhrServer(
                System.getProperty("network.ehr.address", "http://localhost:8888/rest/v1"),
                System.getProperty("network.ehr.username", "guest"),
                System.getProperty("network.ehr.password", "guest"));

            StepUtils.wait(120, TimeUnit.SECONDS, () ->
                HttpUtils.pingUrl(ehrServer.getPingAddress()), ehrServer.getAddress());
        }
    }

    private void initializeFhirServer() throws TimeoutException {
        if (fhirServer == null) {
            fhirServer = new FhirServer(
                System.getProperty("network.fhir.address", "http://localhost:8090"));

            fhirworkServer = new FhirworkServer(
                System.getProperty("network.fhir.address", "http://localhost:8090"));

            StepUtils.wait(120, TimeUnit.SECONDS, () ->
                HttpUtils.pingUrl(fhirServer.getPingAddress()), fhirServer.getAddress());
        }
    }

    protected Person getPersonByName(String name) throws RestServerException {
        for (Person person: empiServer.getPeople()){
            if (Objects.equals(person.getGivenName(), name)){
                return person;
            }
        }
        throw new IllegalStateException();
    }
}
