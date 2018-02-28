package org.ucl.fhirwork.configuration.data;

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
