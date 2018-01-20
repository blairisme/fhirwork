/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.fhir.model;

import java.util.List;

public class FhirPatient
{
    private String id;
    private List<Name> name;
    private List<Identifier> identifier;
    private String gender;

    public FhirPatient(String id, List<Name> name, List<Identifier> identifier, String gender)
    {
        this.id = id;
        this.name = name;
        this.identifier = identifier;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public List<Name> getName() {
        return name;
    }

    public List<Identifier> getIdentifier() {
        return identifier;
    }

    public String getGender() {
        return gender;
    }
}
