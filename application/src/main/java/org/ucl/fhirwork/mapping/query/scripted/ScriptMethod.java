/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping.query.scripted;

/**
 * Options in this enumeration specify callable methods in user provided
 * scripts.
 *
 * @author Blair Butterworth
 */
public enum ScriptMethod
{
    GetQuery        ("getQuery"),
    GetObservations ("getObservations");

    private String name;

    ScriptMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
