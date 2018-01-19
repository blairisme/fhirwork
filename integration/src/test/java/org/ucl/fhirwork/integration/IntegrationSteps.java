/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.StepDefAnnotation;
import org.ucl.fhirwork.integration.data.Patient;
import org.ucl.fhirwork.integration.empi.EmpiServer;

import java.util.List;

@StepDefAnnotation
public class IntegrationSteps
{
    @Given("^the system has the following patients:$")
    public void initializePatients(List<Patient> patients) throws Exception
    {
        EmpiServer empiServer = new EmpiServer("http://localhost:8080", "admin", "admin");
        for (Patient patient: patients) {
            empiServer.addPatient(patient);
        }
    }

    @When("^I search patients")
    public void patientSearch() throws Exception
    {
    }

    @Then("The server response has status code 200")
    public void responseCode()
    {
    }

    @Then("the response is a bundle that contains the patients created")
    public void responseContent()
    {
    }
}
