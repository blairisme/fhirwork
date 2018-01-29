/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.fhir.servlet;

import ca.uhn.fhir.model.dstu2.resource.OperationOutcome;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.ucl.fhirwork.ApplicationService;
import org.ucl.fhirwork.network.fhir.operations.CreatePatientOperation;

import javax.inject.Inject;

@SuppressWarnings("unused")
public class PatientResourceProvider implements IResourceProvider
{
    private ApplicationService applicationService;

    @Inject
    public PatientResourceProvider(ApplicationService applicationService)
    {
        this.applicationService = applicationService;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType()
    {
        return Patient.class;
    }

    @Create
    public MethodOutcome createPatient(@ResourceParam Patient patient) {
        MethodOutcome result = new MethodOutcome();
        OperationOutcome outcome = new OperationOutcome();

        try {
            CreatePatientOperation operation = new CreatePatientOperation(patient);
            Patient response = (Patient)applicationService.execute(operation);
            result.setId(new IdDt("Patient", response.getId().getIdPart(), "1"));
        }
        catch (Throwable error) {
            outcome.addIssue().setDiagnostics(error.getMessage());
            result.setOperationOutcome(outcome);
        }
        return result;
    }

    /*
    @Read
    public Patient getPatientById(@IdParam IdDt patientId)
    {
        try {
            GetPatientOperation executor = new GetPatientOperation(patientId);
            return (Patient)system.execute(executor);
        }
        catch (Exception e) {
            throw new ResourceNotFoundException(Patient.class, patientId);
        }
    }
    */
}
