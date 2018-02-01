package org.ucl.fhirwork.configuration;

public class MappingPath
{
    private String date;
    private String magnitude;
    private String units;

    public MappingPath()
    {
    }

    public MappingPath(String date, String magnitude, String units) {
        this.date = date;
        this.magnitude = magnitude;
        this.units = units;
    }

    public String getDate() {
        return date;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getUnits() {
        return units;
    }
}
