/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping.query.basic;

import ca.uhn.fhir.model.dstu2.composite.QuantityDt;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import org.ucl.fhirwork.configuration.data.BasicMappingConfig;
import org.ucl.fhirwork.mapping.data.ObservationFactory;
import org.ucl.fhirwork.mapping.query.MappingProvider;
import org.ucl.fhirwork.network.ehr.data.ObservationBundle;
import org.ucl.fhirwork.network.ehr.data.QueryBuilder;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BasicMapping implements MappingProvider
{
    private BasicMappingConfig configuration;
    private ObservationFactory observationFactory;

    @Inject
    public BasicMapping(ObservationFactory observationFactory) {
        this.observationFactory = observationFactory;
    }

    public void setConfiguration(BasicMappingConfig configuration) {
        this.configuration = configuration;
    }

    @Override
    public String getQuery(String ehrId)
    {
        QueryBuilder query = new QueryBuilder();
        query.appendSelectStatement(configuration.getText(), configuration.getDate(), "date");
        query.appendSelectStatement(configuration.getText(), configuration.getMagnitude(), "magnitude");
        query.appendSelectStatement(configuration.getText(), configuration.getUnit(), "unit");
        query.appendFromStatement("EHR", "ehr_id/value='" + ehrId + "'");
        query.appendContainsStatement("COMPOSITION", "c");
        query.appendContainsStatement("OBSERVATION", configuration.getText(), configuration.getArchetype());
        return query.build();
    }

    @Override
    public List<Observation> getObservations(String code, String patient, ObservationBundle queryBundle)
    {
        List<Observation> result = new ArrayList<>(queryBundle.getResultSet().size());
        for (Map<String, String> queryResult: queryBundle.getResultSet()){
            QuantityDt quantity = newQuantity(queryResult);
            DateTimeDt effective = newEffective(queryResult);
            result.add(observationFactory.from(patient, code, quantity, effective));
        }
        return result;
    }

    private QuantityDt newQuantity(Map<String, String> queryResult)
    {
        QuantityDt quantity = new QuantityDt();
        quantity.setValue(Double.parseDouble(queryResult.get("magnitude")));
        quantity.setUnit(queryResult.get("unit"));
        quantity.setCode(queryResult.get("unit"));
        quantity.setSystem("http://unitsofmeasure.org");
        return quantity;
    }

    private DateTimeDt newEffective(Map<String, String> queryResult)
    {
        return new DateTimeDt(queryResult.get("date"));
    }
}
