/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.fhir;

import static org.ucl.fhirwork.integration.fhir.FhirEndpoint.Patient;
import static org.ucl.fhirwork.integration.fhir.FhirParameter.*;
import static org.ucl.fhirwork.integration.common.http.HttpHeader.*;
import static org.ucl.fhirwork.integration.common.http.MimeType.*;

import com.google.common.collect.ImmutableMap;

import org.ucl.fhirwork.integration.common.http.RestServer;
import org.ucl.fhirwork.integration.common.http.RestServerException;
import org.ucl.fhirwork.integration.fhir.model.Bundle;
import org.ucl.fhirwork.integration.fhir.model.BundleEntry;
import org.ucl.fhirwork.integration.fhir.model.Patient;
import org.ucl.fhirwork.integration.common.serialization.JsonSerializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FhirServer
{
    private RestServer server;

    public FhirServer(String address)
    {
        this.server = new RestServer(address, new JsonSerializer(), ImmutableMap.of(Accept, Json, ContentType, Json));
    }

    public void addPatient(Patient patient) throws RestServerException
    {
        server.post(Patient, patient, Patient.class, ImmutableMap.of(Format, Json));
    }

    public List<Patient> searchPatients() throws RestServerException
    {
        Bundle bundle = server.get(Patient, Bundle.class, Collections.emptyMap());
        return getPatients(bundle);
    }

    public List<Patient> searchPatientsByIdentifier(String identifier) throws RestServerException
    {
        Bundle bundle = server.get(Patient, Bundle.class, ImmutableMap.of(Identifier, identifier));
        return getPatients(bundle);
    }

    public List<Patient> searchPatientsByGender(String gender) throws RestServerException
    {
        Bundle bundle = server.get(Patient, Bundle.class, ImmutableMap.of(Gender, gender));
        return getPatients(bundle);
    }

    public List<Patient> searchPatientsByFirstName(String firstName) throws RestServerException
    {
        Bundle bundle = server.get(Patient, Bundle.class, ImmutableMap.of(Given, firstName));
        return getPatients(bundle);
    }

    public List<Patient> searchPatientsBySurname(String surname) throws RestServerException
    {
        Bundle bundle = server.get(Patient, Bundle.class, ImmutableMap.of(Family, surname));
        return getPatients(bundle);
    }

    public List<Patient> searchPatientsByGenderAndSurname(String gender, String surname) throws RestServerException
    {
        Bundle bundle = server.get(Patient, Bundle.class, ImmutableMap.of(Gender, gender, Family, surname));
        return getPatients(bundle);
    }

    public void searchObservation(String patient, String code) throws RestServerException
    {
        //String result = server.getFoo(FhirEndpoint.Observation, ImmutableMap.of(Code, code, "identifier", patient, "_format", "json"));
        //result.length();

        /*
            url = "#{$fhir_server_base}/Observation?code=http://loinc.org|3141-9,http://loinc.org|8302-2,http://loinc.org|8287-5,http://loinc.org|58941-6&patient=#{patient_id}&_format=json"
    @response = RestClient.get url, :content_type => :json, :accept => :json
         */

    }

    private List<Patient> getPatients(Bundle bundle)
    {
        List<Patient> result = new ArrayList<>();
        for (BundleEntry bundleEntry: bundle.getEntry()){
            result.add(bundleEntry.getResource());
        }
        return result;
    }
}
