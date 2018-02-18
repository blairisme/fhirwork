package org.ucl.fhirwork.integration.cucumber;

public class HealthData
{
    private String subject;
    private String namespace;
    private String date;
    private int height;
    private int weight;
    private int bmi;
    private int headCircumference;

    public HealthData(String subject, String namespace, String date, int height, int weight, int bmi, int headCircumference) {
        this.subject = subject;
        this.namespace = namespace;
        this.date = date;
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
        this.headCircumference = headCircumference;
    }

    public String getSubject() {
        return subject;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getDate() {
        return date;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getBmi() {
        return bmi;
    }

    public int getHeadCircumference() {
        return headCircumference;
    }
}