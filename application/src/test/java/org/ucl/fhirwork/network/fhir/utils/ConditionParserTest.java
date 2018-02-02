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

import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Test;
import org.ucl.fhirwork.network.fhir.data.SearchParameter;

import java.util.Map;

public class ConditionParserTest
{
    @Test
    public void getSearchParametersTest()
    {
        ConditionParser parser = new ConditionParser();
        String condition = "Patient?identifier=system%7C00001";

        Map<SearchParameter, String> expected = ImmutableMap.of(SearchParameter.Identifier, "system|00001");
        Map<SearchParameter, String> actual = parser.getSearchParameters(condition);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unknownParametersTest()
    {
        ConditionParser parser = new ConditionParser();
        String condition = "Patient?unknownParameter=system%7C00001";
        parser.getSearchParameters(condition);
    }
}
