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

import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.primitive.IdDt;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.network.fhir.data.SearchParameter;
import org.ucl.fhirwork.network.fhir.operations.common.ConditionalOperation;

import java.util.Map;

/**
 * Instances of this class represent the FHIR update patient operation.
 *
 * @author Blair Butterworth
 */
public class UpdatePatientOperation extends ConditionalOperation
{
    private IdDt patientId;
    private Patient patient;

    public UpdatePatientOperation(IdDt patientId, Patient patient) {
        this.patientId = patientId;
        this.patient = patient;
    }

    public UpdatePatientOperation(Patient patient, Map<SearchParameter, Object> searchParameters) {
        super(searchParameters);
        this.patient = patient;
    }

    public IdDt getPatientId() {
        return patientId;
    }

    public Patient getPatient() {
        return patient;
    }
}