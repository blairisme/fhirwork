package org.ucl.fhirwork.mapping.query.basic;

import ca.uhn.fhir.model.dstu2.resource.Observation;
import org.ucl.fhirwork.configuration.data.SimpleMappingConfig;
import org.ucl.fhirwork.mapping.data.ObservationFactory;
import org.ucl.fhirwork.mapping.query.MappingProvider;
import org.ucl.fhirwork.network.ehr.data.ObservationBundle;

import javax.inject.Inject;
import java.util.List;

public class BasicMapping implements MappingProvider
{
    private SimpleMappingConfig configuration;
    private ObservationFactory observationFactory;

    @Inject
    public BasicMapping(ObservationFactory observationFactory)
    {
        this.observationFactory = observationFactory;
    }

    public void setConfiguration(SimpleMappingConfig configuration)
    {
        this.configuration = configuration;
    }

    @Override
    public String getQuery(String ehrId)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getSelectStatement());
        stringBuilder.append(getFromStatement(ehrId));
        stringBuilder.append(getContainsStatement());
        return stringBuilder.toString();
    }

    @Override
    public List<Observation> getObservations(String patientId, ObservationBundle result)
    {
        return observationFactory.fromQueryBundle(patientId, "", result);
    }

    private String getSelectStatement()
    {
        String text = configuration.getText();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select ");
        stringBuilder.append(getSelectStatement(text, configuration.getDate(), "date"));
        stringBuilder.append(", ");
        stringBuilder.append(getSelectStatement(text, configuration.getMagnitude(), "magnitude"));
        stringBuilder.append(", ");
        stringBuilder.append(getSelectStatement(text, configuration.getUnit(), "unit"));
        stringBuilder.append(" ");

        return stringBuilder.toString();
    }

    private String getSelectStatement(String text, String path, String label)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(text);
        stringBuilder.append("/");
        stringBuilder.append(path);
        stringBuilder.append(" as ");
        stringBuilder.append(label);
        return stringBuilder.toString();
    }

    private String getFromStatement(String ehrId)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("from EHR [ehr_id/value='");
        stringBuilder.append(ehrId);
        stringBuilder.append("'] ");
        return stringBuilder.toString();
    }

    private String getContainsStatement()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("contains COMPOSITION c ");
        stringBuilder.append("contains OBSERVATION ");
        stringBuilder.append(configuration.getText());
        stringBuilder.append("[");
        stringBuilder.append(configuration.getArchetype());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
