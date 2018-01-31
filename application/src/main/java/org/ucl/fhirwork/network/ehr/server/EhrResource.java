/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.ehr.server;

import org.ucl.fhirwork.common.http.RestResource;

/**
 * Options in this enumeration represent the addresses of EHR REST web
 * services.
 *
 * @author Blair Butterworth
 */
public enum EhrResource implements RestResource
{
    Composition ("composition"),
    Ehr         ("ehr"),
    Observation ("observation"),
    Query       ("query"),
    Session     ("session"),
    Template    ("template");

    private String path;

    EhrResource(String path)
    {
        this.path = path;
    }

    @Override
    public String getPath()
    {
        return path;
    }
}
