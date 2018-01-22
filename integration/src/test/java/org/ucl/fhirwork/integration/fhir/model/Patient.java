/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.fhir.model;

import org.ucl.fhirwork.integration.cucumber.Profile;

import java.util.Arrays;
import java.util.List;

public class Patient
{
    private String resourceType;
    private List<Name> name;
    private List<Identifier> identifier;
    //private String gender;

    public Patient(List<Name> name, List<Identifier> identifier, String gender)
    {
        this.name = name;
        this.identifier = identifier;
       // this.gender = gender;
        this.resourceType = "Patient";
    }

    public static Patient fromProfile(Profile profile)
    {
        return new Patient(
            Arrays.asList(Name.from(profile.getFirst(), profile.getLast())),
            Arrays.asList(Identifier.from(profile.getId())),
            profile.getGender());
    }

    public List<Name> getName() {
        return name;
    }

    public List<Identifier> getIdentifier() {
        return identifier;
    }

    public String getResourceType() {
        return resourceType;
    }
}
