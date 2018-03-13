package org.ucl.fhirwork.network.fhir.operations.patient;

import ca.uhn.fhir.model.primitive.IdDt;
import org.ucl.fhirwork.network.fhir.data.SearchParameter;
import org.ucl.fhirwork.network.fhir.operations.common.OperationBuilder;

import java.util.Map;

/**
 * Instances of this class construct {@link DeletePatientOperation} instances,
 * given either a patient identifier or a collection of search parameters.
 *
 * @author Blair Butterworth
 */
public class DeletePatientOperationBuilder extends OperationBuilder<DeletePatientOperation>
{
    @Override
    protected DeletePatientOperation newOperation(IdDt identifier) {
        return new DeletePatientOperation(identifier);
    }

    @Override
    protected DeletePatientOperation newOperation(Map<SearchParameter, Object> searchParameters) {
        return new DeletePatientOperation(searchParameters);
    }
}
