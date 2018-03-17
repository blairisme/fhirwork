/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.StepDefAnnotation;
import org.junit.Assert;
import org.ucl.fhirwork.integration.common.http.RestServerException;
import org.ucl.fhirwork.integration.cucumber.Profile;
import org.ucl.fhirwork.integration.empi.EmpiServer;
import org.ucl.fhirwork.integration.empi.model.Person;
import org.ucl.fhirwork.integration.fhir.FhirServer;
import org.ucl.fhirwork.integration.fhir.model.Patient;
import org.ucl.fhirwork.integration.fhir.utils.NameUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

/**
 * Instances of this class provide implements for Cucumber steps relating
 * patient to patient features, create patient etc...
 *
 * @author Blair Butterworth
 */
@StepDefAnnotation
@SuppressWarnings("unused")
public class PatientSteps extends IntegrationSteps
{
    private List<Patient> patients;

    @Before
    public void setup() throws TimeoutException
    {
        super.setup();
        patients = new ArrayList<>();
    }

    @After
    public void tearDown() throws Exception
    {
        getEmpiServer().removePeople();
    }

    @Given("^the system has no patients$")
    public void initializeEmpty() throws RestServerException
    {
        getEmpiServer().removePeople();
    }

    @Given("^the system has the following patients:$")
    public void initializePatients(List<Profile> profiles) throws RestServerException
    {
        getEmpiServer().removePeople();
        for (Profile profile: profiles) {
            Person person = Person.fromProfile(profile);
            getEmpiServer().addPerson(person);
        }
    }

    @When("^the user adds a patient with the following data:$")
    public void createPatient(List<Profile> profiles) throws RestServerException
    {
        Profile profile = profiles.get(0);
        Patient patient = Patient.fromProfile(profile);
        getFhirServer().addPatient(patient);
    }

    @When("^the user searches for patients$")
    public void readAll() throws RestServerException
    {
        patients = getFhirServer().searchPatients();
    }

    @When("^the user searches for patients by id for patient \"(.*)\"$")
    public void readByInternalId(String patientName) throws RestServerException
    {
        Person person = getPersonByName(patientName);
        String personId = person.getPersonId();

        Patient patient = getFhirServer().readPatient(personId);
        patients = Arrays.asList(patient);
    }

    @When("^the user searches for patients by id \"(.*)\"$")
    public void readByInternalIdExplicit(String id) throws RestServerException
    {
        try {
            Patient patient = getFhirServer().readPatient(id);
            patients = Arrays.asList(patient);
        }
        catch (RestServerException error){
            if (error.getStatusCode() != 404){
                throw error;
            }
        }
    }

    @When("^the user searches for patients with identifier \"(.*)\" and namespace \"(.*)\"$")
    public void patientSearchByIdentifier(String identifier, String namespace) throws RestServerException
    {
        patients = getFhirServer().searchPatientsByIdentifier(namespace + "|" + identifier);
    }

    @When("^the user searches for patients with (male|female) gender$")
    public void patientSearchByGender(String gender) throws RestServerException
    {
        patients = getFhirServer().searchPatientsByGender(gender);
    }

    @When("^the user searches for patients with last name \"(.*)\"$")
    public void patientSearchBySurname(String surname) throws RestServerException
    {
        patients = getFhirServer().searchPatientsBySurname(surname);
    }

    @When("^the user searches for patients with (male|female) gender and last name \"(.*)\"$")
    public void patientSearchByGenderAndSurname(String gender, String surname) throws RestServerException
    {
        patients = getFhirServer().searchPatientsByGenderAndSurname(gender, surname);
    }

    @When("^the user deletes the patient named \"(.*)\" using their id$")
    public void deletePatientById(String patientName) throws RestServerException
    {
        Person person = getPersonByName(patientName);
        getFhirServer().deletePatientById(person.getPersonId());
    }

    @When("^the user deletes the patient named \"(.*)\"$")
    public void deletePatientByName(String givenName) throws RestServerException
    {
        getFhirServer().deletePatientByGivenName(givenName);
    }

    @When("^the user changes the (first name|last name) of \"(.*)\" to \"(.*)\"$")
    public void updatePatient(String property, String name, String value) throws RestServerException
    {
        Person person = getPersonByName(name);
        String personId = person.getPersonId();

        Patient patient = getFhirServer().readPatient(personId);
        if (property.equalsIgnoreCase("first name")){
            patient.setFirstName(value);
        }
        if (property.equalsIgnoreCase("last name")){
            patient.setLastName(value);
        }
        try {
            getFhirServer().updatePatient(personId, patient);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Then("^the user should receive a list of (\\d) patients$")
    public void assertSearchList(int patientCount)
    {
        Assert.assertEquals(patientCount, patients.size());
    }

    @Then("^the user should receive a patient named (.*)")
    public void assertSearchResult(String patientName)
    {
        Predicate<Patient> predicate = patient -> NameUtils.hasGivenName(patient, patientName);
        Patient patient = patients.stream().filter(predicate).findFirst().orElse(null);
        Assert.assertNotNull(patient);
    }

    @Then("^the system should contain a patient named \"(.*)\"")
    public void assertPatientExists(String patientName) throws RestServerException
    {
        Person person = getPersonByName(patientName);
        Assert.assertNotNull(person);
    }

    @Then("^the system should contain (\\d) patients$")
    public void assertPatientCount(int count) throws RestServerException
    {
        List<Person> people = getEmpiServer().getPeople();
        Assert.assertEquals(count, people.size());
    }
}
