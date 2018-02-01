package org.ucl.fhirwork.configuration;

public class MappingSpecification
{
    private MappingPath path;
    private String archetype;
    private String text;

    public MappingSpecification()
    {
    }

    public MappingSpecification(MappingPath path, String archetype, String text) {
        this.path = path;
        this.archetype = archetype;
        this.text = text;
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
}