/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.fhir.model;

import java.util.Arrays;
import java.util.List;

public class Name
{
    private String use;
    private List<String> given;
    private List<String> family;

    public Name(String use, List<String> given, List<String> family) {
        this.use = use;
        this.given = given;
        this.family = family;
    }

    public static Name from(String firstName, String lastName)
    {
        return new Name(
            "usual",
            Arrays.asList(firstName),
            Arrays.asList(lastName));
    }

    public String getUse() {
        return use;
    }

    public List<String> getGiven() {
        return given;
    }

    public List<String> getFamily() {
        return family;
    }
}