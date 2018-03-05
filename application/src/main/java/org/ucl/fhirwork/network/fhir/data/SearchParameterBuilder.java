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

import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.rest.param.DateParam;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.TokenParam;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

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
public class SearchParameterBuilder
{
    private Map<SearchParameter, Object> parameters;

    public SearchParameterBuilder()
    {
        this.parameters = new HashMap<>();
    }

    public void append(String conditional) throws IllegalArgumentException
    {
        for (NameValuePair queryParameter: getQueryParameters(conditional)) {
            SearchParameter searchParameter = SearchParameter.fromString(queryParameter.getName());
            append(searchParameter, queryParameter.getValue());
        }
    }

    public void append(SearchParameter key, String value)
    {
        switch (key){
            case GivenName:
            case FamilyName:
            case Gender: {
                append(key, newString(value));
                break;
            }
            case Identifier: {
                append(key, newToken(value));
                break;
            }
            case BirthDate: {
                append(key, newDate(value));
                break;
            }
        }
    }

    public void append(SearchParameter key, TokenParam value)
    {
        if (value != null) {
            parameters.put(key, value);
        }
    }

    public void append(SearchParameter key, DateParam value)
    {
        if (value != null) {
            parameters.put(key, value);
        }
    }

    public void append(SearchParameter key, StringDt value)
    {
        if (value != null) {
            parameters.put(key, value);
        }
    }

    public Map<SearchParameter, Object> build()
    {
        return parameters;
    }

    private List<NameValuePair> getQueryParameters(String conditional)
    {
        return URLEncodedUtils.parse(URI.create("http://fhirwork.org/" + conditional), "UTF-8");
    }

    private TokenParam newToken(String value)
    {
        return TokenParamUtils.fromText(value);
    }

    private DateParam newDate(String value)
    {
        return new DateParam(value);
    }

    private StringDt newString(String value)
    {
        return new StringDt(value);
    }
}
