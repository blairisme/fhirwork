/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping.query.scripted;

import ca.uhn.fhir.model.dstu2.composite.QuantityDt;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import com.google.common.reflect.Invokable;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.ucl.fhirwork.configuration.data.ScriptedMappingConfig;
import org.ucl.fhirwork.mapping.data.ObservationFactory;
import org.ucl.fhirwork.mapping.query.MappingProvider;
import org.ucl.fhirwork.network.ehr.data.ObservationBundle;
import org.ucl.fhirwork.network.ehr.data.ObservationResult;
import org.ucl.fhirwork.network.ehr.data.QueryBuilder;

import javax.inject.Inject;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScriptedMapping implements MappingProvider
{
    private Invocable engine;
    private ObservationFactory observationFactory;

    @Inject
    public ScriptedMapping(ObservationFactory observationFactory)
    {
        this.observationFactory = observationFactory;
    }

    public void setConfiguration(ScriptedMappingConfig configuration)
    {
        try {
            ScriptEngineManager engineManager = new ScriptEngineManager();
            ScriptEngine scriptEngine = engineManager.getEngineByName("nashorn");
            scriptEngine.eval(configuration.getScript());
            engine = (Invocable)scriptEngine;
        }
        catch (ScriptException error) {
            throw new RuntimeException(error);
        }
    }

    @Override
    public String getQuery(String ehrId)
    {
        ScriptQuery queryDetails = getScriptQuery(ehrId);

        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.appendSelectStatement("skeletal_age", "data[at0001]/origin/value", "date");
        queryBuilder.appendSelectStatement("skeletal_age", queryDetails.getSelectors().iterator().next());
        queryBuilder.appendFromStatement("EHR", "ehr_id/value='" + ehrId + "'");
        queryBuilder.appendContainsStatement("COMPOSITION", "c");
        queryBuilder.appendContainsStatement("OBSERVATION", "skeletal_age", queryDetails.getArchetype());

        return queryBuilder.build();
    }

    @Override
    public List<Observation> getObservations(String code, String patient, ObservationBundle bundle)
    {
        List<Observation> result = new ArrayList<>(bundle.getResultSet().size());
        for (Map<String, String> queryResult: bundle.getResultSet()){
            QuantityDt quantity = newQuantity(queryResult);
            DateTimeDt effective = newEffective(queryResult);
            result.add(observationFactory.from(patient, code, quantity, effective));
        }
        return result;
    }

    private ScriptQuery getScriptQuery(String ehrId)
    {
        try {
            ScriptObjectMirror mirror = (ScriptObjectMirror)engine.invokeFunction("getQuery", ehrId);
            ScriptQuery query = new ScriptQuery(mirror);
            return query;
        }
        catch (NoSuchMethodException error) {
            throw new RuntimeException(error);
        }
        catch (ScriptException error) {
            throw new RuntimeException(error);
        }
    }

    private ScriptQuantity getScriptQuantity(Map<String, String> result)
    {
        try {
            ScriptObjectMirror mirror = (ScriptObjectMirror)engine.invokeFunction("getQuantity", result);
            ScriptQuantity quantity = new ScriptQuantity(mirror);
            return quantity;
        }
        catch (NoSuchMethodException error) {
            throw new RuntimeException(error);
        }
        catch (ScriptException error) {
            throw new RuntimeException(error);
        }
    }

    private QuantityDt newQuantity(Map<String, String> queryResult)
    {
        ScriptQuantity scriptInfo = getScriptQuantity(queryResult);

        QuantityDt quantity = new QuantityDt();
        quantity.setValue(scriptInfo.getValue());
        quantity.setUnit(scriptInfo.getUnit());
        quantity.setCode(quantity.getUnit());
        quantity.setSystem("network://unitsofmeasure.org");

        return quantity;
    }

    private DateTimeDt newEffective(Map<String, String> queryResult)
    {
        return new DateTimeDt(queryResult.get("date"));
    }
}
