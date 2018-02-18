/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.fhir.model;

public class Identifier {
    private String system;
    private String value;

    public Identifier(String system, String value) {
        this.system = system;
        this.value = value;
    }

    public static Identifier from(String id){
        return new Identifier("SSN", id);
    }

    public String getSystem() {
        return system;
    }

    public String getValue() {
        return value;
    }
}