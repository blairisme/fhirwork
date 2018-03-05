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

import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.DateParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.NotImplementedOperationException;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.ucl.fhirwork.mapping.ExecutorService;
import org.ucl.fhirwork.network.fhir.data.SearchParameter;
import org.ucl.fhirwork.network.fhir.data.SearchParameterBuilder;
import org.ucl.fhirwork.network.fhir.operations.patient.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static org.ucl.fhirwork.network.fhir.data.ExceptionUtils.getFrameworkException;
import static org.ucl.fhirwork.network.fhir.data.MethodOutcomeUtils.patientResult;
import static org.ucl.fhirwork.network.fhir.data.SearchParameter.*;

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
    private ExecutorService executorService;

    @Inject
    public PatientResourceProvider(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Patient.class;
    }

    @Create
    public MethodOutcome create(@ResourceParam Patient patient)
    {
        try {
            CreatePatientOperation operation = new CreatePatientOperation(patient);
            Patient result = (Patient)executorService.execute(operation);
            return patientResult(result);
        }
        catch (Throwable error) {
            throw getFrameworkException(error);
        }
    }

    @Delete
    public void delete(
        @IdParam IdDt patientId,
        @OptionalParam(name = Patient.SP_IDENTIFIER) TokenParam identifier,
        @OptionalParam(name = Patient.SP_GIVEN) StringDt givenName,
        @OptionalParam(name = Patient.SP_FAMILY) StringDt familyName,
        @OptionalParam(name = Patient.SP_GENDER) StringDt gender,
        @OptionalParam(name = Patient.SP_BIRTHDATE) DateParam birthDate)
    {
        try {
            DeletePatientOperationBuilder operationBuilder = new DeletePatientOperationBuilder();
            operationBuilder.append(patientId);
            operationBuilder.append(Identifier, identifier);
            operationBuilder.append(GivenName, givenName);
            operationBuilder.append(FamilyName, familyName);
            operationBuilder.append(Gender, gender);
            operationBuilder.append(BirthDate, birthDate);

            DeletePatientOperation operation = operationBuilder.build();
            executorService.execute(operation);
        }
        catch (Throwable error) {
            throw getFrameworkException(error);
        }
    }

    @Read
    public Patient read(@IdParam IdDt patientId)
    {
        try {
            ReadPatientOperation operation = new ReadPatientOperation(patientId);
            return (Patient)executorService.execute(operation);
        }
        catch (Throwable error) {
            throw getFrameworkException(error);
        }
    }

    @Search
    @SuppressWarnings("unchecked")
    public List<Patient> search(
        @OptionalParam(name = Patient.SP_IDENTIFIER) TokenParam identifier,
        @OptionalParam(name = Patient.SP_GIVEN) StringDt givenName,
        @OptionalParam(name = Patient.SP_FAMILY) StringDt familyName,
        @OptionalParam(name = Patient.SP_GENDER) StringDt gender,
        @OptionalParam(name = Patient.SP_BIRTHDATE) DateParam birthDate)
    {
        try {
            SearchParameterBuilder parameterBuilder = new SearchParameterBuilder();
            parameterBuilder.append(Identifier, identifier);
            parameterBuilder.append(GivenName, givenName);
            parameterBuilder.append(FamilyName, familyName);
            parameterBuilder.append(Gender, gender);
            parameterBuilder.append(SearchParameter.BirthDate, birthDate);

            ReadPatientOperation operation = new ReadPatientOperation(parameterBuilder.build());
            return (List<Patient>)executorService.execute(operation);
        }
        catch (Throwable error) {
            throw getFrameworkException(error);
        }
    }

    @Update
    public MethodOutcome update(@IdParam IdDt patientId, @ResourceParam Patient patient)
    {
        try {
            UpdatePatientOperation operation = new UpdatePatientOperation(patientId, patient);
            Patient result = (Patient)executorService.execute(operation);
            return patientResult(result);
        }
        catch (Throwable error) {
            throw getFrameworkException(error);
        }
    }

    @Update
    public MethodOutcome updateConditional(
            @ResourceParam Patient patient,
            @IdParam IdDt id,
            @ConditionalUrlParam String condition)
    {
        try {
            UpdatePatientOperation operation = new UpdatePatientOperation(patient, getSearchParameters(condition));
            Patient result = (Patient)executorService.execute(operation);
            return patientResult(result);
        }
        catch (Throwable error) {
            throw getFrameworkException(error);
        }

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
