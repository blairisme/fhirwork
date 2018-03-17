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

import jdk.nashorn.api.scripting.ScriptObjectMirror;

/**
 * Instances of this class represent an observation, returned from a user
 * provided mapping script.
 *
 * @author Blair Butterworth
 */
public class ScriptObservation
{
    private ScriptObjectMirror objectMirror;

    public ScriptObservation(ScriptObjectMirror objectMirror) {
        this.objectMirror = objectMirror;
    }

    public String getDate(){
        return (String)objectMirror.getMember("date");
    }

    public String getUnit(){
        return (String)objectMirror.getMember("unit");
    }

    public String getUnitSystem() {
        return (String)objectMirror.getMember("unitSystem");
    }

    public Double getValue(){
        return (Double)objectMirror.getMember("value");
    }
}
