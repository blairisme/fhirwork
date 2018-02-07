package org.ucl.fhirwork.configuration;

public class MappingSpecification
{
    private MappingPath path;
    private String archetype;
    private String text;
    private String loinc;

    public MappingSpecification()
    {
    }

    public MappingSpecification(MappingPath path, String archetype, String text, String loinc) {
        this.path = path;
        this.archetype = archetype;
        this.text = text;
        this.loinc = loinc;
    }

    public MappingPath getPath() {
        return path;
    }

    public String getArchetype() {
        return archetype;
    }

    public String getText() {
        return text;
    }

    public String getLoinc() {
        return loinc;
    }
}