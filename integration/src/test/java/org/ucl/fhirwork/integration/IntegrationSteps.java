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

@StepDefAnnotation
public class IntegrationSteps
{

    @When("^I search patients")
    public void patientSearch() throws Exception
    {
        System.out.println("******* patientSearch ********");
    }

    @Then("The server response has status code 200")
    public void responseCode()
    {
        System.out.println("******* responseCode ********");
    }

    @Then("the response is a bundle that contains the patients created")
    public void responseContent()
    {
        System.out.println("******* responseContent ********");
    }
}
