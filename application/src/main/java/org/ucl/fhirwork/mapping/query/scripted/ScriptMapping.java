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

import ca.uhn.fhir.model.dstu2.resource.Observation;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.ucl.fhirwork.configuration.data.ScriptedMappingConfig;
import org.ucl.fhirwork.mapping.data.ObservationFactory;
import org.ucl.fhirwork.mapping.query.MappingProvider;
import org.ucl.fhirwork.network.ehr.data.ObservationBundle;

import javax.inject.Inject;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;

import static org.ucl.fhirwork.mapping.query.scripted.ScriptEngines.Nashorn;
import static org.ucl.fhirwork.mapping.query.scripted.ScriptMethod.GetObservations;
import static org.ucl.fhirwork.mapping.query.scripted.ScriptMethod.GetQuery;

/**
 * Instances of this class convert observations to OpenEHR AQL queries using a
 * user provided Javascript script.
 *
 * @author Blair Butterworth
 */
public class ScriptMapping implements MappingProvider
{
    private Invocable engine;
    private ObservationFactory observationFactory;

    @Inject
    public ScriptMapping(ObservationFactory observationFactory)
    {
        this.observationFactory = observationFactory;
    }

    public void setConfiguration(ScriptedMappingConfig configuration)
    {
        try {
            ScriptEngineManager engineManager = new ScriptEngineManager();
            ScriptEngine scriptEngine = engineManager.getEngineByName(Nashorn.getName());
            scriptEngine.eval(configuration.getScript());
            engine = (Invocable)scriptEngine;
        }
        catch (ScriptException error) {
            throw new ScriptExecutionException(error);
        }
    }

    @Override
    public String getQuery(String ehrId)
    {
        try {
            return (String)engine.invokeFunction(GetQuery.getName(), ehrId);
        }
        catch (NoSuchMethodException | ScriptException error) {
            throw new ScriptExecutionException(error);
        }
    }

    @Override
    public List<Observation> getObservations(String code, String patientId, ObservationBundle bundle)
    {
        try {
            ScriptObjectMirror mirror = (ScriptObjectMirror)engine.invokeFunction(GetObservations.getName(), bundle.getResultSet());
            ScriptArray<ScriptObjectMirror> results = new ScriptArray<>(mirror);
            return getObservations(results, patientId, code);
        }
        catch (NoSuchMethodException | ScriptException error) {
            throw new ScriptExecutionException(error);
        }
    }

    private List<Observation> getObservations(ScriptArray<ScriptObjectMirror> scriptObservations, String patientId, String code) {
        List<Observation> result = new ArrayList<>(scriptObservations.length());
        for (ScriptObjectMirror scriptMirror: scriptObservations) {
            ScriptObservation scriptObservation = new ScriptObservation(scriptMirror);
            result.add(observationFactory.from(scriptObservation, patientId, code));
        }
        return result;
    }
}
