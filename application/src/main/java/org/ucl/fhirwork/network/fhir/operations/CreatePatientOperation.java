package org.ucl.fhirwork.network.fhir.operations;

import ca.uhn.fhir.model.dstu2.resource.Patient;
import org.ucl.fhirwork.common.framework.Operation;

public class CreatePatientOperation implements Operation
{
    private Patient patient;

    public CreatePatientOperation(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }
}
