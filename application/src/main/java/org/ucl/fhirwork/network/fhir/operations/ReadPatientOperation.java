/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.fhir.operations;

import ca.uhn.fhir.model.primitive.IdDt;
import org.ucl.fhirwork.common.framework.Operation;

/**
 * Instances of this class represent the FHIR read patient operation.
 *
 * @author Blair Butterworth
 */
public class ReadPatientOperation implements Operation
{
    private IdDt patientId;

    public ReadPatientOperation(IdDt patientId) {
        this.patientId = patientId;
    }

    public IdDt getPatientId() {
        return patientId;
    }
}
