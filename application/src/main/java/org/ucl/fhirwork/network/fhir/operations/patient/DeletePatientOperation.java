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
import org.ucl.fhirwork.network.fhir.data.SearchParameter;

import java.util.Map;

/**
 * Instances of this class represent the FHIR delete patient operation.
 *
 * @author Blair Butterworth
 */
public class DeletePatientOperation implements Operation
{
    private IdDt patientId;
    private Map<SearchParameter, String> searchParameters;

    public DeletePatientOperation(IdDt patientId) {
        this.patientId = patientId;
    }

    public DeletePatientOperation(IdDt patientId, Map<SearchParameter, String> searchParameters) {
        this.patientId = patientId;
        this.searchParameters = searchParameters;
    }

    public IdDt getPatientId() {
        return patientId;
    }

    public Map<SearchParameter, String> getSearchParameters() {
        return searchParameters;
    }
}
