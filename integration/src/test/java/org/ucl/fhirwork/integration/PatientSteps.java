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
import org.ucl.fhirwork.integration.cucumber.Profile;
import org.ucl.fhirwork.integration.empi.EmpiServer;
import org.ucl.fhirwork.integration.empi.model.Person;
import org.ucl.fhirwork.integration.fhir.model.Patient;
import org.ucl.fhirwork.integration.fhir.FhirServer;
import org.ucl.fhirwork.integration.fhir.utils.NameUtils;
import sun.security.krb5.internal.PAData;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@StepDefAnnotation
@SuppressWarnings("unused")
public class PatientSteps
{
    private FhirServer fhirServer;
    private EmpiServer empiServer;
    private List<Patient> patients;

    @Before
    public void setup()
    {
        patients = Collections.emptyList();
        fhirServer = new FhirServer("http://localhost:8090");
        empiServer = new EmpiServer("http://localhost:8080", "admin", "admin");
    }

    @Given("^the system has no patients$")
    public void initializeEmpty() throws Exception
    {
        empiServer.removePeople();
    }

    @Given("^the system has the following patients:$")
    public void initializePatients(List<Profile> profiles) throws Exception
    {
        empiServer.removePeople();
        for (Profile profile: profiles) {
            Person person = Person.fromProfile(profile);
            empiServer.addPerson(person);
        }
    }

    @When("^the user adds a patient with the following data:$")
    public void createPatient(List<Profile> profiles) throws Exception
    {
        Profile profile = profiles.get(0);
        Patient patient = Patient.fromProfile(profile);
        fhirServer.addPatient(patient);
    }

    @When("^the user searches for patients$")
    public void patientSearch() throws Exception
    {
        patients = fhirServer.searchPatients();
    }

    @When("^the user searches for patients by id for patient \"(.*)\"$")
    public void patientSearchById(String patientName) throws Exception
    {
        for (Person person: empiServer.getPeople()){
            if (Objects.equals(person.getGivenName(), patientName)){
                Patient patient = fhirServer.readPatient(person.getPersonId());
                patients = Arrays.asList(patient);
            }
        }
    }

    @When("^the user searches for patients with identifier \"(.*)\" and namespace \"(.*)\"$")
    public void patientSearchByIdentifier(String identifier, String namespace) throws Exception
    {
        patients = fhirServer.searchPatientsByIdentifier(namespace + "|" + identifier);
    }

    @When("^the user searches for patients with (male|female) gender$")
    public void patientSearchByGender(String gender) throws Exception
    {
        patients = fhirServer.searchPatientsByGender(gender);
    }

    @When("^the user searches for patients with last name \"(.*)\"$")
    public void patientSearchBySurname(String surname) throws Exception
    {
        patients = fhirServer.searchPatientsBySurname(surname);
    }

    @When("^the user searches for patients with (male|female) gender and last name \"(.*)\"$")
    public void patientSearchByGenderAndSurname(String gender, String surname) throws Exception
    {
        patients = fhirServer.searchPatientsByGenderAndSurname(gender, surname);
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

    @Then("^the system should contain a patient named (.*)")
    public void assertPatientExist(String patientName) throws  Exception
    {
        List<Person> people = empiServer.getPeople();
        Predicate<Person> predicate = person -> Objects.equals(person.getGivenName(), patientName);
        Person person = people.stream().filter(predicate).findFirst().orElse(null);
        Assert.assertNotNull(person);
    }
}
