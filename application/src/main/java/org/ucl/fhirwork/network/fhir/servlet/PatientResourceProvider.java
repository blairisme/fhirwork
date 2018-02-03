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
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.DateParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.InternalErrorException;
import ca.uhn.fhir.rest.server.exceptions.NotImplementedOperationException;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.ucl.fhirwork.ApplicationService;
import org.ucl.fhirwork.network.fhir.data.SearchParameter;
import org.ucl.fhirwork.network.fhir.operations.patient.*;
import org.ucl.fhirwork.network.fhir.data.SearchParameterBuilder;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

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
    public MethodOutcome create(@ResourceParam Patient patient)
    {
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

    @Delete
    public void delete(@IdParam IdDt patientId)
    {
        try {
            DeletePatientOperation operation = new DeletePatientOperation(patientId);
            applicationService.execute(operation);
        }
        catch (Exception e) {
            throw new ResourceNotFoundException(Patient.class, patientId);
        }
    }

    @Delete
    public void deleteConditional(@IdParam IdDt patientId, @ConditionalUrlParam String condition)
    {
        try {
            DeletePatientOperation operation = new DeletePatientOperation(patientId, getSearchParameters(condition));
            applicationService.execute(operation);
        }
        catch (Exception e) {
            throw new ResourceNotFoundException(Patient.class, patientId);
        }
    }

    @Read
    public Patient read(@IdParam IdDt patientId)
    {
        try {
            ReadPatientOperation operation = new ReadPatientOperation(patientId);
            return (Patient)applicationService.execute(operation);
        }
        catch (Exception e) {
            throw new ResourceNotFoundException(Patient.class, patientId);
        }
    }

    @Search
    @SuppressWarnings("unchecked")
    public List<Patient> readConditional(
            @OptionalParam(name = Patient.SP_IDENTIFIER) TokenParam identifier,
            @OptionalParam(name = Patient.SP_GIVEN) StringDt givenName,
            @OptionalParam(name = Patient.SP_FAMILY) StringDt familyName,
            @OptionalParam(name = Patient.SP_GENDER) StringDt gender,
            @OptionalParam(name = Patient.SP_BIRTHDATE) DateParam birthDate)
    {
        try {
            SearchParameterBuilder parameterBuilder = new SearchParameterBuilder();
            parameterBuilder.append(SearchParameter.Identifier, identifier);
            parameterBuilder.append(SearchParameter.GivenName, givenName);
            parameterBuilder.append(SearchParameter.FamilyName, familyName);
            parameterBuilder.append(SearchParameter.Gender, gender);
            parameterBuilder.append(SearchParameter.BirthDate, birthDate);

            ReadPatientOperation operation = new ReadPatientOperation(parameterBuilder.build());
            return (List<Patient>)applicationService.execute(operation);
        }
        catch (Throwable error) {
            throw new InternalErrorException(error);
        }
    }

    @Update
    public MethodOutcome update(@IdParam IdDt patientId, @ResourceParam Patient patient)
    {
        MethodOutcome result = new MethodOutcome();
        OperationOutcome outcome = new OperationOutcome();

        try {
            UpdatePatientOperation operation = new UpdatePatientOperation(patientId, patient);
            Patient response = (Patient)applicationService.execute(operation);
            result.setId(new IdDt("Patient", response.getId().getIdPart(), "1"));
        }
        catch (Throwable error) {
            outcome.addIssue().setDiagnostics(error.getMessage());
            result.setOperationOutcome(outcome);
        }
        return result;
    }

    @Update
    public MethodOutcome updateConditional(@ResourceParam Patient patient, @IdParam IdDt id, @ConditionalUrlParam String condition)
    {
        MethodOutcome result = new MethodOutcome();
        OperationOutcome outcome = new OperationOutcome();
        Map<SearchParameter, Object> parameters = getSearchParameters(condition);

        try {
            UpdatePatientOperation operation = new UpdatePatientOperation(patient, parameters);
            Patient response = (Patient)applicationService.execute(operation);
            result.setId(new IdDt("Patient", response.getId().getIdPart(), "1"));
        }
        catch (Throwable error) {
            outcome.addIssue().setDiagnostics(error.getMessage());
            result.setOperationOutcome(outcome);
        }
        return result;
    }

    private Map<SearchParameter, Object> getSearchParameters(String condition)
    {
        try {
            SearchParameterBuilder parameterBuilder = new SearchParameterBuilder();
            parameterBuilder.append(condition);
            return parameterBuilder.build();
        }
        catch (IllegalArgumentException error) {
            throw new NotImplementedOperationException("Unsupported search parameter in service call: " + condition);
        }
    }
}
