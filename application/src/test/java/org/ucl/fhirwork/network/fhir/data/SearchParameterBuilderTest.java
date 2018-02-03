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

import ca.uhn.fhir.rest.param.TokenParam;
import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.network.fhir.data.SearchParameter;
import org.ucl.fhirwork.network.fhir.data.SearchParameterBuilder;

import java.util.Map;

public class SearchParameterBuilderTest
{
    @Test
    public void getSearchParametersTest()
    {
        SearchParameterBuilder builder = new SearchParameterBuilder();
        builder.append("Patient?identifier=system%7C00001");

        Map<SearchParameter, Object> expected = ImmutableMap.of(SearchParameter.Identifier, new TokenParam("system", "00001"));
        Map<SearchParameter, Object> actual = builder.build();

        Assert.assertEquals(expected.size(), actual.size());
        Assert.assertEquals(expected.get(0), actual.get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void unknownParametersTest()
    {
        SearchParameterBuilder builder = new SearchParameterBuilder();
        builder.append("Patient?unknownParameter=system%7C00001");
    }
}
