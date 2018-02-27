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

public class ScriptQuantity
{
    private ScriptObjectMirror objectMirror;

    public ScriptQuantity(ScriptObjectMirror objectMirror) {
        this.objectMirror = objectMirror;
    }

    public Double getValue(){
        return (Double)objectMirror.getMember("value");
    }

    public String getUnit(){
        return (String)objectMirror.getMember("unit");
    }
}
