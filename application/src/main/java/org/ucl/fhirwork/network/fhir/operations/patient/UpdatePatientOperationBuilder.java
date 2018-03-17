package org.ucl.fhirwork.network.fhir.operations.patient;

import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.primitive.IdDt;
import org.ucl.fhirwork.network.fhir.data.SearchParameter;
import org.ucl.fhirwork.network.fhir.operations.common.OperationBuilder;

import java.util.Map;

/**
 * Instances of this class construct {@link UpdatePatientOperation} instances,
 * given either a patient identifier or a collection of search parameters.
 *
 * @author Blair Butterworth
 */
public class UpdatePatientOperationBuilder extends OperationBuilder<UpdatePatientOperation>
{
    private Patient patient;

    public void append(Patient patient) {
        this.patient = patient;
    }

    @Override
    protected UpdatePatientOperation newOperation(IdDt identifier) {
        return new UpdatePatientOperation(identifier, patient);
    }

    @Override
    protected UpdatePatientOperation newOperation(Map<SearchParameter, Object> searchParameters) {
        return new UpdatePatientOperation(searchParameters, patient);
    }
}
