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

public class Bundle
{
    private List<BundleEntry> entry;

    public Bundle(List<BundleEntry> entry) {
        this.entry = entry;
    }

    public List<BundleEntry> getEntry() {
        return entry;
    }
}
