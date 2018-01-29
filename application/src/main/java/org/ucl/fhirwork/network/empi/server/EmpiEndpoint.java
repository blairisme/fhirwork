/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.empi.server;

import org.ucl.fhirwork.common.http.RestEndpoint;

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

    @Override
    public String toString()
    {
        return path;
    }
}