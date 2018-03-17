/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.configuration.data;

/**
 * Instances of this class contain a Javascript script, used to generate an EHR
 * AQL query from a given LOINC code.
 *
 * @author Blair Butterworth
 */
public class ScriptedMappingConfig
{
    private String code;
    private String script;

    public ScriptedMappingConfig(String code, String script) {
        this.code = code;
        this.script = script;
    }

    public String getCode() {
        return code;
    }

    public String getScript() {
        return script;
    }
}
