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
import org.ucl.fhirwork.integration.common.http.RestServer;
import org.ucl.fhirwork.integration.common.http.RestServerException;
import org.ucl.fhirwork.integration.common.serialization.JsonSerializer;

import static org.ucl.fhirwork.integration.common.http.HttpHeader.Accept;
import static org.ucl.fhirwork.integration.common.http.HttpHeader.ContentType;
import static org.ucl.fhirwork.integration.common.http.MimeType.Json;

public class FhirworkServer
{
    private RestServer server;

    public FhirworkServer(String address) {
        this.server = new RestServer(address, new JsonSerializer(), ImmutableMap.of(Accept, Json, ContentType, Json));
    }

    public void removeMapping(String code) throws RestServerException {
        server.get(FhirworkEndpoint.RemoveMapping, ImmutableMap.of("code", code));
    }
}
