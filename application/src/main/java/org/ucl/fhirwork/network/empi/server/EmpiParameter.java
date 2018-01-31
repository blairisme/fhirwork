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

/**
 * Options in this enumeration specify identifiers for REST query parameter
 * key value pairs used in EMPI web service calls.
 *
 * @author Blair Butterworth
 */
public enum EmpiParameter
{
    FirstRecord ("firstRecord"),
    MaxRecords  ("maxRecords"),
    PersonId    ("personId");

    private String value;

    EmpiParameter(String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return value;
    }
}