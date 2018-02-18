/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.empi;

import org.ucl.fhirwork.integration.common.http.RestEndpoint;

public enum EmpiEndpoint implements RestEndpoint
{
    AddPerson       ("openempi-admin/openempi-ws-rest/person-manager-resource/addPerson"),
    Authenticate    ("openempi-admin/openempi-ws-rest/security-resource/authenticate"),
    LoadAllPersons  ("openempi-admin/openempi-ws-rest/person-query-resource/loadAllPersonsPaged"),
    RemovePerson    ("openempi-admin/openempi-ws-rest/person-manager-resource/removePersonById");

    private String path;

    private EmpiEndpoint(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return path;
    }
}