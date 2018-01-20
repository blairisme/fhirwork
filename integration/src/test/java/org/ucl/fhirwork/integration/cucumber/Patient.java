/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.cucumber;

public class Patient
{
    private String id;
    private String first;
    private String last;
    private String gender;

    public Patient(String id, String first, String last, String gender) {
        this.id = id;
        this.first = first;
        this.last = last;
    }

    public String getId() {
        return id;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getGender() {
        return gender;
    }
}