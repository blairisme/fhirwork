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

import org.ucl.fhirwork.common.http.RestResource;

/**
 * Options in this enumeration represent the addresses of EMPI REST web
 * services.
 *
 * @author Blair Butterworth
 */
public enum EmpiResource implements RestResource
{
    AddPerson               ("openempi-admin/openempi-ws-rest/person-manager-resource/addPerson"),
    Authenticate            ("openempi-admin/openempi-ws-rest/security-resource/authenticate"),
    FindPersonById          ("openempi-admin/openempi-ws-rest/person-query-resource/findPersonById"),
    FindPersonsByAttributes ("openempi-admin/openempi-ws-rest/person-query-resource/findPersonsByAttributes"),
    LoadPerson              ("openempi-admin/openempi-ws-rest/person-query-resource/loadPerson"),
    LoadAllPersons          ("openempi-admin/openempi-ws-rest/person-query-resource/loadAllPersonsPaged"),
    RemovePerson            ("openempi-admin/openempi-ws-rest/person-manager-resource/removePersonById"),
    UpdatePerson            ("openempi-admin/openempi-ws-rest/person-manager-resource/updatePerson");

    private String path;

    private EmpiResource(String path)
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