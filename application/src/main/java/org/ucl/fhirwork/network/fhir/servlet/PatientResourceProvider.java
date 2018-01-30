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
import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.ucl.fhirwork.ApplicationService;
import org.ucl.fhirwork.network.fhir.operations.CreatePatientOperation;
import org.ucl.fhirwork.network.fhir.operations.ReadPatientOperation;

import javax.inject.Inject;

/**
 * Instances of this class provide implement functions defined in the FHIR
 * specification related to Patients. Once implemented these operation can
 * be then be called by FHIR clients. For more information see:
 *
 *      http://hapifhir.io/doc_rest_operations.html
 *
 * @author Blair Butterworth
 */
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

    @Create
    public MethodOutcome createPatientConditional(
            @ResourceParam Patient thePatient,
            @ConditionalUrlParam String theConditionalUrl)
    {
        //theConditional will have a value like "Patient?identifier=system%7C00001"
        throw new UnsupportedOperationException();
    }

    @Read
    public Patient readPatient(@IdParam IdDt patientId)
    {
        try {
            ReadPatientOperation operation = new ReadPatientOperation(patientId);
            return (Patient)applicationService.execute(operation);
        }
        catch (Exception e) {
            throw new ResourceNotFoundException(Patient.class, patientId);
        }
    }

    @Update
    public MethodOutcome update(
            @IdParam IdDt patientId,
            @ResourceParam Patient patient)
    {
        throw new UnsupportedOperationException();
    }

    @Update
    public MethodOutcome updatePatientConditional(
            @ResourceParam Patient thePatient,
            @IdParam IdDt theId,
            @ConditionalUrlParam String theConditional)
    {
        //theConditional will have a value like "Patient?identifier=system%7C00001"
        throw new UnsupportedOperationException();
    }

    @Delete
    public void deletePatient(@IdParam IdDt patientId)
    {
        throw new UnsupportedOperationException();
    }

    @Delete
    public void deletePatientConditional(
            @IdParam IdDt theId,
            @ConditionalUrlParam String theConditionalUrl)
    {
        //theConditional will have a value like "Patient?identifier=system%7C00001"
        throw new UnsupportedOperationException();
    }
}
