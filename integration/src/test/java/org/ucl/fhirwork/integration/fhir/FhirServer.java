/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.fhir;

import com.google.common.collect.ImmutableMap;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.ucl.fhirwork.integration.common.http.HttpStatus;
import org.ucl.fhirwork.integration.fhir.model.Bundle;
import org.ucl.fhirwork.integration.fhir.model.BundleEntry;
import org.ucl.fhirwork.integration.fhir.model.Patient;
import org.ucl.fhirwork.integration.common.serialization.JsonSerializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FhirServer
{
    private static final String ACCEPT_JSON = "application/json";
    private static final String ACCEPT_HEADER = "accept";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    private static final String GENDER_PARAMETER = "gender";
    private static final String GIVEN_PARAMETER = "given";
    private static final String FAMILY_PARAMETER = "family";
    private static final String IDENTIFIER_PARAMETER = "identifier";

    private static final String PATIENT_ENDPOINT = "fhir/Patient";

    private String server;
    private JsonSerializer serializer;

    public FhirServer(String server)
    {
        this.server = server.endsWith("/") ? server : server + "/";
        this.serializer = new JsonSerializer();
    }

    public void addPatient(Patient patient) throws FhirServerException
    {
        post(PATIENT_ENDPOINT, patient, Patient.class, ImmutableMap.of("_format", "application/json"));
    }

    public List<Patient> searchPatients() throws FhirServerException
    {
        Bundle bundle = get(PATIENT_ENDPOINT, Bundle.class, Collections.emptyMap());
        return getPatients(bundle);
    }

    public List<Patient> searchPatientsByIdentifier(String identifier) throws FhirServerException
    {
        Bundle bundle = get(PATIENT_ENDPOINT, Bundle.class, ImmutableMap.of(IDENTIFIER_PARAMETER, identifier));
        return getPatients(bundle);
    }

    public List<Patient> searchPatientsByGender(String gender) throws FhirServerException
    {
        Bundle bundle = get(PATIENT_ENDPOINT, Bundle.class, ImmutableMap.of(GENDER_PARAMETER, gender));
        return getPatients(bundle);
    }

    public List<Patient> searchPatientsByFirstName(String firstName) throws FhirServerException
    {
        Bundle bundle = get(PATIENT_ENDPOINT, Bundle.class, ImmutableMap.of(GIVEN_PARAMETER, firstName));
        return getPatients(bundle);
    }

    public List<Patient> searchPatientsBySurname(String surname) throws FhirServerException
    {
        Bundle bundle = get(PATIENT_ENDPOINT, Bundle.class, ImmutableMap.of(FAMILY_PARAMETER, surname));
        return getPatients(bundle);
    }

    public List<Patient> searchPatientsByGenderAndSurname(String gender, String surname) throws FhirServerException
    {
        Bundle bundle = get(PATIENT_ENDPOINT, Bundle.class, ImmutableMap.of(GENDER_PARAMETER, gender, FAMILY_PARAMETER, surname));
        return getPatients(bundle);
    }

    private List<Patient> getPatients(Bundle bundle)
    {
        List<Patient> result = new ArrayList<>();
        for (BundleEntry bundleEntry: bundle.getEntry()){
            result.add(bundleEntry.getResource());
        }
        return result;
    }

    private <T> T get(String endpoint, Class<T> type, Map<String, Object> parameters) throws FhirServerException
    {
        try {
            HttpRequest request = Unirest.get(server + endpoint)
                .header(ACCEPT_HEADER, ACCEPT_JSON)
                .queryString(parameters);
            HttpResponse<String> response = request.asString();

            if (! HttpStatus.isSuccessful(response.getStatus())) {
                throw new FhirServerException(response.getStatus());
            }
            return serializer.deserialize(response.getBody(), type);
        }
        catch (UnirestException exception){
            throw new FhirServerException(exception);
        }
    }

    private <T> void post(String endPoint, T value, Class<T> type, Map<String, Object> parameters) throws FhirServerException
    {
        String requestBody = serializer.serialize(value, type);

        try {
            RequestBodyEntity request = Unirest.post(server + endPoint)
                    .header(ACCEPT_HEADER, ACCEPT_JSON)
                    .header(CONTENT_TYPE_HEADER, CONTENT_TYPE_JSON)
                    .queryString(parameters)
                    .body(requestBody);
            HttpResponse<String> response = request.asString();

            if (! HttpStatus.isSuccessful(response.getStatus())) {
                throw new FhirServerException(response.getStatus());
            }
        }
        catch (UnirestException exception){
            throw new FhirServerException(exception);
        }
    }
}
