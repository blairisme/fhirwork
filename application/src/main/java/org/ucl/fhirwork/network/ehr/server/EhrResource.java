
package org.ucl.fhirwork.network.ehr.server;

import org.ucl.fhirwork.common.network.Rest.RestResource;


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


    private EhrResource(String path)
    {
        this.path = path;
    }

    @Override
    public String getPath()
    {
        return path;
    }
}
