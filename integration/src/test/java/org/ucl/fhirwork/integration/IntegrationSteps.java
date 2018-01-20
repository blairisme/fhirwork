/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.StepDefAnnotation;
import org.junit.Assert;
import org.ucl.fhirwork.integration.data.Patient;
import org.ucl.fhirwork.integration.empi.EmpiServer;
import org.ucl.fhirwork.integration.fhir.model.FhirPatient;
import org.ucl.fhirwork.integration.fhir.FhirServer;
import org.ucl.fhirwork.integration.fhir.utils.NameUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@StepDefAnnotation
@SuppressWarnings("unused")
public class IntegrationSteps
{
    private FhirServer fhirServer;
    private EmpiServer empiServer;
    private List<FhirPatient> patients;

    @Before
    public void setup()
    {
        patients = Collections.emptyList();
        fhirServer = new FhirServer("http://localhost:8090");
        empiServer = new EmpiServer("http://localhost:8080", "admin", "admin");
    }

    @Given("^the system has the following patients:$")
    public void initializePatients(List<Patient> patients) throws Exception
    {
        empiServer.removeAllPatients();
        for (Patient patient: patients) {
            empiServer.addPatient(patient);
        }
    }

    @When("^the user searches for patients$")
    public void patientSearch() throws Exception
    {
        patients = fhirServer.searchPatients();
    }

    @Then("^the user should receive a list of (\\d) patients$")
    public void assertSearchList(int patientCount)
    {
        Assert.assertEquals(patientCount, patients.size());
    }

    @Then("^the user should receive a patient named (.*)")
    public void assertSearchResult(String patientName)
    {
        Predicate<FhirPatient> predicate = patient -> NameUtils.hasGivenName(patient, patientName);
        FhirPatient patient = patients.stream().filter(predicate).findFirst().orElse(null);
        Assert.assertNotNull(patient);
    }
}
