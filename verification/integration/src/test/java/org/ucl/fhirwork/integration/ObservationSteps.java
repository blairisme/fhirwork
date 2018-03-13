package org.ucl.fhirwork.integration;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.StepDefAnnotation;
import org.junit.Assert;
import org.ucl.fhirwork.integration.common.http.RestServerException;
import org.ucl.fhirwork.integration.cucumber.HealthData;
import org.ucl.fhirwork.integration.ehr.model.*;
import org.ucl.fhirwork.integration.ehr.model.composition.GrowthChartComposition;
import org.ucl.fhirwork.integration.ehr.model.composition.HeightWeightComposition;
import org.ucl.fhirwork.integration.empi.model.Person;
import org.ucl.fhirwork.integration.fhir.model.Observation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@StepDefAnnotation
@SuppressWarnings("unused")
public class ObservationSteps extends IntegrationSteps
{
    private List<Observation> observations;

    @Before
    public void setup() throws Exception
    {
        super.setup();
        observations = new ArrayList<>();
    }

    @Given("^the system has the following health data:$")
    public void initializeHealthData(List<HealthData> healthData) throws IOException, RestServerException
    {
        setConfiguration();
        createTemplates();
        removeHealthRecords(healthData);
        createHealthRecords(healthData);
        //removeCompositions(healthData);
        createCompositions(healthData);
    }

    private void setConfiguration() throws RestServerException
    {
        fhirworkServer.removeMapping("39156-5");
        fhirworkServer.removeMapping("8287-5");
        fhirworkServer.removeMapping("37362-1");
    }

    private void createTemplates() throws IOException, RestServerException
    {
        createTemplate("RIPPLE - Personal Notes.v1", "templates/Ripple_Personal_Notes_v1.xml");
        createTemplate("RIPPLE - Height_Weight.v1", "templates/Ripple_Height_Weight_v1.xml");
        //createTemplate("Smart Growth Chart Data.v0", "templates/Smart_Growth_Chart_Data_v0.xml");
    }

    private void createTemplate(String id, String resource) throws IOException, RestServerException
    {
        if (! ehrServer.templateExists(id)){
            TemplateReference template = new TemplateReference(resource);
            ehrServer.addTemplate(template);
        }
    }

    private void removeHealthRecords(List<HealthData> dataList) throws RestServerException
    {
        for (HealthData data: dataList){
            removeHealthRecord(data);
        }
    }

    private void removeHealthRecord(HealthData healthData) throws RestServerException
    {
        String subjectId = healthData.getSubject();
        String subjectNamespace = healthData.getNamespace();

        if (ehrServer.ehrExists(subjectId, subjectNamespace)){
            HealthRecord healthRecord = ehrServer.getEhr(subjectId, subjectNamespace);
            ehrServer.removeEhr(healthRecord.getEhrId());
        }
    }

    private void createHealthRecords(List<HealthData> dataList) throws RestServerException
    {
        for (HealthData data: dataList){
            createHealthRecord(data);
        }
    }

    private void createHealthRecord(HealthData healthData) throws RestServerException
    {
        String subjectId = healthData.getSubject();
        String subjectNamespace = healthData.getNamespace();

        if (! ehrServer.ehrExists(subjectId, subjectNamespace)){
            ehrServer.createEhr(subjectId, subjectNamespace);
        }
    }

    private HealthRecord getHealthRecord(HealthData healthData) throws RestServerException
    {
        String subjectId = healthData.getSubject();
        String subjectNamespace = healthData.getNamespace();
        return ehrServer.getEhr(subjectId, subjectNamespace);
    }

    private void removeCompositions(List<HealthData> dataList) throws RestServerException
    {
        for (HealthData healthData: dataList){
            HealthRecord healthRecord = getHealthRecord(healthData);
            removeCompositions(healthRecord);
        }
    }

    private void removeCompositions(HealthRecord record) throws RestServerException
    {
        List<Composition> compositions = ehrServer.getCompositions(record.getEhrId());
        for (Composition composition: compositions){
            ehrServer.removeComposition(composition);
        }
    }

    private void createCompositions(List<HealthData> healthDataList) throws RestServerException
    {
        for (HealthData healthData: healthDataList){
            HealthRecord healthRecord = getHealthRecord(healthData);
            createComposition(healthData, healthRecord);
        }
    }

    private void createComposition(HealthData data, HealthRecord record) throws RestServerException
    {
        //GrowthChartComposition composition = GrowthChartComposition.fromHealthData(data);
        //ehrServer.createComposition(record, composition, GrowthChartComposition.class);

        HeightWeightComposition composition = HeightWeightComposition.fromHealthData(data);
        ehrServer.createComposition(record, composition, HeightWeightComposition.class);
    }

    @When("^the user searches for all observations belonging to patient \"(.*)\"$")
    public void readAllObservations(String patient) throws RestServerException
    {
        Person person = getPersonByName(patient);
        observations = fhirServer.searchPatientObservations(person.getPersonId());
    }

    @When("^the user searches for observations belonging to patient \"(.*)\" with LOINC code \"(.*)\"$")
    public void readPatientObservations(String name, String code) throws RestServerException
    {
        Person person = getPersonByName(name);
        String codes = getCodeParameter(code);
        observations = fhirServer.searchPatientObservations(person.getPersonId(), codes);
    }

    @When("^the user searches for observations belonging to subject \"(.*)\" with LOINC code \"(.*)\"$")
    public void readSubjectObservations(String name, String code) throws RestServerException
    {
        Person person = getPersonByName(name);
        String codes = getCodeParameter(code);
        observations = fhirServer.searchSubjectObservations(person.getPersonId(), codes);
    }

    @Then("^the user should receive a list of (\\d*) observations$")
    public void assertObservationList(int observationsCount)
    {
        Assert.assertEquals(observationsCount, observations.size());
    }

    private String getCodeParameter(String code)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (String subSequence: code.split(",")){
            stringBuilder.append("http://loinc.org|");
            stringBuilder.append(subSequence);
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
