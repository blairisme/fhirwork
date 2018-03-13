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
import java.util.Collection;

/**
 * Instances of this class represent an OpenEHR AQL query returned from a user
 * provided mapping script.
 *
 * @author Blair Butterworth
 */
public class ScriptQuery
{
    private ScriptObjectMirror objectMirror;

    public ScriptQuery(ScriptObjectMirror objectMirror) {
        this.objectMirror = objectMirror;
    }

    public String getArchetype(){
        return (String)objectMirror.getMember("archetype");
    }

    @SuppressWarnings("unchecked")
    public Collection<String> getSelectors() {
        ScriptObjectMirror selectors = (ScriptObjectMirror)objectMirror.getMember("selectors");
        Collection<Object> values = selectors.values();
        return (Collection)values;
    }
}
