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

    public FhirPatient(String id, List<Name> name, List<Identifier> identifier) {
        this.id = id;
        this.name = name;
        this.identifier = identifier;
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
}
