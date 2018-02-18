/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.fhir.model;

import java.util.Collections;
import java.util.List;

public class PatientBundle
{
    private List<PatientBundleEntry> entry;

    public PatientBundle(List<PatientBundleEntry> entry) {
        this.entry = entry;
    }

    public List<PatientBundleEntry> getEntry() {
        if (entry != null) {
            return entry;
        }
        return Collections.emptyList();
    }
}
