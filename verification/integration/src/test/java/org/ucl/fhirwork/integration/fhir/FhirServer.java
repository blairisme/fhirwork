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
import org.ucl.fhirwork.integration.fhir.model.*;
import org.ucl.fhirwork.integration.common.serialization.JsonSerializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FhirServer
{
    private String address;
    private RestServer server;

    public FhirServer(String address)
    {
        this.address = address;
        this.server = new RestServer(address, new JsonSerializer(), ImmutableMap.of(Accept, Json, ContentType, Json));
    }

    public String getAddress() {
        return address;
    }

    public void addPatient(Patient patient) throws RestServerException
    {
        server.post(Patient, patient, Patient.class, ImmutableMap.of(Format, Json));
    }

    public Patient readPatient(String patientId) throws RestServerException
    {
        return server.get(Patient.getPath() + "/" + patientId, Patient.class, Collections.emptyMap());
    }

    public void deletePatientById(String patientId) throws RestServerException
    {
        server.delete(Patient.getPath() + "/" + patientId, Collections.emptyMap());
    }

    public void deletePatientByGivenName(String givenName) throws RestServerException
    {
        server.delete(Patient, ImmutableMap.of(Given, givenName));
    }

    public void updatePatient(String patientId, Patient patient) throws RestServerException
    {
        server.put(Patient.getPath() + "/" + patientId, patient, Patient.class, Collections.emptyMap());
    }

    public List<Patient> searchPatients() throws RestServerException
    {
        PatientBundle bundle = server.get(Patient, PatientBundle.class, Collections.emptyMap());
        return getPatients(bundle);
    }

    public List<Patient> searchPatientsByIdentifier(String identifier) throws RestServerException
    {
        PatientBundle bundle = server.get(Patient, PatientBundle.class, ImmutableMap.of(Identifier, identifier));
        return getPatients(bundle);
    }

    public List<Patient> searchPatientsByGender(String gender) throws RestServerException
    {
        PatientBundle bundle = server.get(Patient, PatientBundle.class, ImmutableMap.of(Gender, gender));
        return getPatients(bundle);
    }

    public List<Patient> searchPatientsByFirstName(String firstName) throws RestServerException
    {
        PatientBundle bundle = server.get(Patient, PatientBundle.class, ImmutableMap.of(Given, firstName));
        return getPatients(bundle);
    }

    public List<Patient> searchPatientsBySurname(String surname) throws RestServerException
    {
        PatientBundle bundle = server.get(Patient, PatientBundle.class, ImmutableMap.of(Family, surname));
        return getPatients(bundle);
    }

    public List<Patient> searchPatientsByGenderAndSurname(String gender, String surname) throws RestServerException
    {
        PatientBundle bundle = server.get(Patient, PatientBundle.class, ImmutableMap.of(Gender, gender, Family, surname));
        return getPatients(bundle);
    }

    public List<Observation> searchPatientObservations(String patient) throws RestServerException
    {
        ObservationBundle bundle = server.get(FhirEndpoint.Observation, ObservationBundle.class, ImmutableMap.of(FhirParameter.Patient, patient, "_format", "json"));
        return getObservations(bundle);
    }

    public List<Observation> searchPatientObservations(String patient, String codes) throws RestServerException
    {
        ObservationBundle bundle = server.get(FhirEndpoint.Observation, ObservationBundle.class,
                ImmutableMap.of(Code, codes, FhirParameter.Patient, patient, "_format", "json"));
        return getObservations(bundle);
    }

    public List<Observation> searchSubjectObservations(String subject, String codes) throws RestServerException
    {
        ObservationBundle bundle = server.get(FhirEndpoint.Observation, ObservationBundle.class,
                ImmutableMap.of(Code, codes, FhirParameter.Subject, subject, "_format", "json"));
        return getObservations(bundle);
    }

    public boolean ping()
    {
        try{
            searchPatients();
            return true;
        }
        catch (RestServerException error){
            return false;
        }
    }

    private List<Patient> getPatients(PatientBundle bundle)
    {
        List<Patient> result = new ArrayList<>();
        for (PatientBundleEntry bundleEntry: bundle.getEntry()){
            result.add(bundleEntry.getResource());
        }
        return result;
    }

    private List<Observation> getObservations(ObservationBundle bundle)
    {
        List<Observation> result = new ArrayList<>();
        for (ObservationBundleEntry bundleEntry: bundle.getEntry()){
            result.add(bundleEntry.getResource());
        }
        return result;
    }
}
