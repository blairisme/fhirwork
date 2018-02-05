package org.ucl.fhirwork.integration.fhir.model;

import java.util.Collections;
import java.util.List;

public class ObservationBundle
{
    private List<ObservationBundleEntry> entry;

    public ObservationBundle(List<ObservationBundleEntry> entry) {
        this.entry = entry;
    }

    public List<ObservationBundleEntry> getEntry() {
        if (entry != null) {
            return entry;
        }
        return Collections.emptyList();
    }
}
