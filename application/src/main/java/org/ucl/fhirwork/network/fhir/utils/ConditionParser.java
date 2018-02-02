/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.fhir.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.ucl.fhirwork.network.fhir.data.SearchParameter;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Instances of this class extract the search parameters from the URL used to
 * invoke a FHIR web service call.
 *
 * @author Blair Butterworth
 */
public class ConditionParser
{
    public Map<SearchParameter, String> getSearchParameters(String conditional) throws IllegalArgumentException
    {
        Map<SearchParameter, String> result = new HashMap<>();
        for (NameValuePair queryParameter: getQueryParameters(conditional))
        {
            SearchParameter searchParameter = SearchParameter.fromString(queryParameter.getName());
            result.put(searchParameter, queryParameter.getValue());
        }
        return result;
    }

    private List<NameValuePair> getQueryParameters(String conditional)
    {
        URI syntheticUri = URI.create("http://fhir.com/" + conditional);
        return URLEncodedUtils.parse(syntheticUri, "UTF-8");
    }
}
