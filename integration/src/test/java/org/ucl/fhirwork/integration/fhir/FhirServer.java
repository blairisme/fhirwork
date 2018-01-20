/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.fhir;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.ucl.fhirwork.integration.fhir.model.Bundle;
import org.ucl.fhirwork.integration.fhir.model.BundleEntry;
import org.ucl.fhirwork.integration.fhir.model.FhirPatient;
import org.ucl.fhirwork.integration.serialization.JsonSerializer;

import java.util.ArrayList;
import java.util.List;

public class FhirServer
{
    private static final String ACCEPT_JSON = "application/json";
    private static final String ACCEPT_HEADER = "accept";

    private static final String PATIENT_ENDPOINT = "fhir/Patient";

    private String server;
    private JsonSerializer serializer;

    public FhirServer(String server)
    {
        this.server = server.endsWith("/") ? server : server + "/";
        this.serializer = new JsonSerializer();
    }
    
    public List<FhirPatient> searchPatients() throws FhirServerException
    {
        List<FhirPatient> result = new ArrayList<>();
        Bundle bundle = get(PATIENT_ENDPOINT, Bundle.class);

        for (BundleEntry bundleEntry: bundle.getEntry()){
            result.add(bundleEntry.getResource());
        }
        return result;
    }

    private <T> T get(String endpoint, Class<T> type) throws FhirServerException
    {
        try {
            GetRequest request = Unirest.get(server + endpoint)
                .header(ACCEPT_HEADER, ACCEPT_JSON);
            HttpResponse<String> response = request.asString();

            if (response.getStatus() != 200){
                throw new FhirServerException(response.getStatus());
            }
            return serializer.deserialize(response.getBody(), type);
        }
        catch (UnirestException exception){
            throw new FhirServerException(exception);
        }
    }
}
