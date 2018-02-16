package org.ucl.fhirwork.integration;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.StepDefAnnotation;
import org.junit.Assert;
import org.ucl.fhirwork.integration.common.http.HttpStatus;
import org.ucl.fhirwork.integration.common.http.RestServerException;
import org.ucl.fhirwork.integration.cucumber.HealthData;
import org.ucl.fhirwork.integration.cucumber.StepUtils;
import org.ucl.fhirwork.integration.ehr.EhrServer;
import org.ucl.fhirwork.integration.ehr.model.*;
import org.ucl.fhirwork.integration.fhir.FhirServer;
import org.ucl.fhirwork.integration.fhir.model.Observation;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@StepDefAnnotation
@SuppressWarnings("unused")
public class ObservationSteps
{
    private static boolean serversPinged = false;
    private FhirServer fhirServer;
    private EhrServer ehrServer;
    private List<Observation> observations;

    @Before
    public void setup() throws Exception
    {
        fhirServer = new FhirServer(
                System.getProperty("network.fhir.address", "http://localhost:8090"));
        ehrServer = new EhrServer(
            System.getProperty("network.ehr.address", "http://localhost:8888/rest/v1"),
            System.getProperty("network.ehr.username", "guest"),
            System.getProperty("network.ehr.password", "guest"));

        if (! serversPinged) {
            StepUtils.wait(60, TimeUnit.SECONDS, () -> ehrServer.ping());
            StepUtils.wait(60, TimeUnit.SECONDS, () -> fhirServer.ping());
            serversPinged = true;
        }
    }

    @Given("^the system has the following health data:$")
    public void initializeHealthData(List<HealthData> healthData) throws IOException, RestServerException
    {
        installTemplates();
        for (HealthData data: healthData){
            HealthRecord record = getHealthRecord(data);
            createComposition(data, record);
        }
    }

    private void installTemplates() throws IOException, RestServerException
    {
        installTemplate("RIPPLE - Personal Notes.v1", "templates/Ripple_Personal_Notes_v1.xml");
        installTemplate("Smart Growth Chart Data.v0", "templates/Smart_Growth_Chart_Data_v0.xml");
    }

    private void installTemplate(String id, String resource) throws IOException, RestServerException
    {
        if (! ehrServer.templateExists(id)){
            TemplateReference template = new TemplateReference(resource);
            ehrServer.addTemplate(template);
        }
    }

    private HealthRecord getHealthRecord(HealthData healthData) throws RestServerException
    {
        String subjectId = healthData.getSubject();
        String subjectNamespace = healthData.getNamespace();

        if (! ehrServer.ehrExists(subjectId, subjectNamespace)){
            return ehrServer.createEhr(subjectId, subjectNamespace);
        }
        return ehrServer.getEhr(subjectId, subjectNamespace);
    }

    private void createComposition(HealthData data, HealthRecord record) throws RestServerException
    {
        GrowthChartComposition composition = GrowthChartComposition.fromHealthData(data);
        ehrServer.createComposition(record, composition, GrowthChartComposition.class);
    }

    private void removeCompositions(HealthRecord record) throws RestServerException
    {
        List<Composition> compositions = ehrServer.getCompositions(record.getEhrId());
        for (Composition composition: compositions){
            ehrServer.removeComposition(composition);
        }
    }

    @When("^the user searches for observations$")
    public void observationSearch() throws RestServerException
    {
        //observations = fhirServer.searchObservation("SSN|1", "http://loinc.org|3141-9");
    }

    @Then("^the user should receive a list of (\\d) observations$")
    public void assertObservationList(int observationsCount)
    {
        //Assert.assertEquals(observationsCount, observations.size());
    }
}
