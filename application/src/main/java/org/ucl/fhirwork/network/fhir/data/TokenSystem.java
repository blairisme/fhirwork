/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.fhir.data;

import ca.uhn.fhir.model.primitive.UriDt;

/**
 * Options in this enumeration represent different system of observation
 * tokens, LOINC codes for example.
 *
 * @author Blair Butterworth
 */
public enum TokenSystem
{
    Loinc (new UriDt("network://loinc.org"));

    private UriDt systemUri;

    private TokenSystem(UriDt systemUri){
        this.systemUri = systemUri;
    }

    public UriDt getUri(){
        return systemUri;
    }
}
