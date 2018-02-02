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

/**
 * Options in this enumeration specify supported FHIR search parameter
 * identifiers. The full list of all possible search parameters can be found
 * at the following address.
 *
 *      http://hl7.org/fhir/searchparameter-registry.html
 *
 * @author Blair Butterworth
 */
public enum SearchParameter
{
    Identifier;

    public static SearchParameter fromString(String text)
    {
        switch (text.toLowerCase())
        {
            case "identifier": return Identifier;
            default: throw new IllegalArgumentException("Unknown search parameter: " + text);
        }
    }
}
