package org.ucl.fhirwork.mapping.query;

import ca.uhn.fhir.model.dstu2.resource.Observation;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.ucl.fhirwork.configuration.data.ScriptedMappingConfig;
import org.ucl.fhirwork.mapping.data.ObservationFactory;
import org.ucl.fhirwork.network.ehr.data.ObservationResult;

import javax.inject.Inject;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptedMapping implements MappingProvider
{
    private ScriptEngine engine;
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
            engine = engineManager.getEngineByName("nashorn");
            engine.eval(configuration.getScript());
        }
        catch (ScriptException error) {
            throw new RuntimeException(error);
        }
    }

    @Override
    public String getQuery(String ehrId)
    {
        try {
            Invocable invocable = (Invocable)engine;
            return (String)invocable.invokeFunction("getQuery", ehrId);
        }
        catch (NoSuchMethodException error) {
            throw new RuntimeException(error);
        }
        catch (ScriptException error) {
            throw new RuntimeException(error);
        }
    }

    @Override
    public Observation getObservation(String patientId, ObservationResult result)
    {
        try {
            Invocable invocable = (Invocable)engine;
            ScriptObjectMirror observation = (ScriptObjectMirror)invocable.invokeFunction("getObservation", result);
            //return observationFactory.
            return null;
        }
        catch (NoSuchMethodException error) {
            throw new RuntimeException(error);
        }
        catch (ScriptException error) {
            throw new RuntimeException(error);
        }
    }

    private Observation getObservation(ScriptObjectMirror observation)
    {
        return null;
    }
}
