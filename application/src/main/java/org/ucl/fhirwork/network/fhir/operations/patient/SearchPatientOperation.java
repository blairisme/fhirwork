/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */
package org.ucl.fhirwork.network.fhir.operations.patient;

import ca.uhn.fhir.model.primitive.IdDt;
import org.ucl.fhirwork.common.framework.Operation;

/**
 * Instances of this class represent the FHIR search patient operation.
 *
 * @author Alperen Karaoglu
 */
public class SearchPatientOperation implements Operation {
    private boolean found;
    private IdDt patientId;

    public SearchPatientOperation(boolean found, IdDt patientId) {
        this.found = found;
        this.patientId = patientId;
    }

    public boolean searchPatientByID(){
        return found;
    }
}
