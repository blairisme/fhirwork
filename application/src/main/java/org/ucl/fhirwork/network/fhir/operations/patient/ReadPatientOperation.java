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
import org.apache.commons.lang3.Validate;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.network.fhir.data.SearchParameter;
import org.ucl.fhirwork.network.fhir.operations.common.ConditionalOperation;

import java.util.Map;

/**
 * Instances of this class represent the FHIR read patient operation.
 *
 * @author Blair Butterworth
 */
public class ReadPatientOperation extends ConditionalOperation
{
    private IdDt patientId;

    public ReadPatientOperation(IdDt patientId) {
        Validate.notNull(patientId);
        Validate.notNull(patientId.getValue());
        this.patientId = patientId;
    }

    public ReadPatientOperation(Map<SearchParameter, Object> parameters){
        super(parameters);
    }

    public IdDt getPatientId() {
        return patientId;
    }
}
