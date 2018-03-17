package org.ucl.fhirwork.integration;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.StepDefAnnotation;
import org.junit.Assert;
import org.ucl.fhirwork.integration.common.http.RestServerException;
import org.ucl.fhirwork.integration.cucumber.HealthData;
import org.ucl.fhirwork.integration.ehr.model.Composition;
import org.ucl.fhirwork.integration.ehr.model.HealthRecord;
import org.ucl.fhirwork.integration.ehr.model.TemplateReference;
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
    private boolean destroyHealthRecords;
    private List<Observation> observations;

    @Before
    public void setup() throws Exception
    {
        super.setup();
        observations = new ArrayList<>();
        destroyHealthRecords = Boolean.valueOf(System.getProperty("data.ehr.destroy", "false"));
    }

    @Given("^the system has the following health data:$")
    public void initializeHealthData(List<HealthData> healthData) throws IOException, RestServerException
    {
        setConfiguration();
        createTemplates();
        removeHealthRecords(healthData);
        createHealthRecords(healthData);
        removeCompositions(healthData);
        createCompositions(healthData);
    }

    private void setConfiguration() throws RestServerException
    {
        getFhirworkServer().removeMapping("39156-5");
        getFhirworkServer().removeMapping("8287-5");
        getFhirworkServer().removeMapping("37362-1");
    }

    private void createTemplates() throws IOException, RestServerException
    {
        createTemplate("RIPPLE - Personal Notes.v1", "templates/Ripple_Personal_Notes_v1.xml");
        createTemplate("RIPPLE - Height_Weight.v1", "templates/Ripple_Height_Weight_v1.xml");
        //createTemplate("Smart Growth Chart Data.v0", "templates/Smart_Growth_Chart_Data_v0.xml");
    }

    private void createTemplate(String id, String resource) throws IOException, RestServerException
    {
        if (! getEhrServer().templateExists(id)){
            TemplateReference template = new TemplateReference(resource);
            getEhrServer().addTemplate(template);
        }
    }

    private void removeHealthRecords(List<HealthData> dataList) throws RestServerException
    {
        if (destroyHealthRecords) {
            for (HealthData data : dataList) {
                removeHealthRecord(data);
            }
        }
    }

    private void removeHealthRecord(HealthData healthData) throws RestServerException
    {
        String subjectId = healthData.getSubject();
        String subjectNamespace = healthData.getNamespace();

        if (getEhrServer().ehrExists(subjectId, subjectNamespace)){
            HealthRecord healthRecord = getEhrServer().getEhr(subjectId, subjectNamespace);
            getEhrServer().removeEhr(healthRecord.getEhrId());
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

        if (! getEhrServer().ehrExists(subjectId, subjectNamespace)){
            getEhrServer().createEhr(subjectId, subjectNamespace);
        }
    }

    private HealthRecord getHealthRecord(HealthData healthData) throws RestServerException
    {
        String subjectId = healthData.getSubject();
        String subjectNamespace = healthData.getNamespace();
        return getEhrServer().getEhr(subjectId, subjectNamespace);
    }

    private void removeCompositions(List<HealthData> dataList) throws RestServerException
    {
        if (! destroyHealthRecords) {
            for (HealthData healthData : dataList) {
                HealthRecord healthRecord = getHealthRecord(healthData);
                removeCompositions(healthRecord);
            }
        }
    }

    private void removeCompositions(HealthRecord record) throws RestServerException
    {
        List<Composition> compositions = getEhrServer().getCompositions(record.getEhrId());
        for (Composition composition: compositions){
            getEhrServer().removeComposition(composition);
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
        //getEhrServer().createComposition(record, composition, GrowthChartComposition.class);

        HeightWeightComposition composition = HeightWeightComposition.fromHealthData(data);
        getEhrServer().createComposition(record, composition, HeightWeightComposition.class);
    }

    @When("^the user searches for all observations belonging to patient \"(.*)\"$")
    public void readAllObservations(String patient) throws RestServerException
    {
        Person person = getPersonByName(patient);
        observations = getFhirServer().searchPatientObservations(person.getPersonId());
    }

    @When("^the user searches for observations belonging to patient \"(.*)\" with LOINC code \"(.*)\"$")
    public void readPatientObservations(String name, String code) throws RestServerException
    {
        Person person = getPersonByName(name);
        String codes = getCodeParameter(code);
        observations = getFhirServer().searchPatientObservations(person.getPersonId(), codes);
    }

    @When("^the user searches for observations belonging to subject \"(.*)\" with LOINC code \"(.*)\"$")
    public void readSubjectObservations(String name, String code) throws RestServerException
    {
        Person person = getPersonByName(name);
        String codes = getCodeParameter(code);
        observations = getFhirServer().searchSubjectObservations(person.getPersonId(), codes);
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
