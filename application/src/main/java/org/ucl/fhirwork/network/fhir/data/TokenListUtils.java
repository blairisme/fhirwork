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

import ca.uhn.fhir.model.base.composite.BaseCodingDt;
import ca.uhn.fhir.model.primitive.UriDt;
import ca.uhn.fhir.rest.param.TokenOrListParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Instances of this class provide utility functions from operating with
 * {@link TokenOrListParam} objects.
 *
 * @author Blair Butterworth
 */
public class TokenListUtils
{
    private TokenListUtils() {
    }

    public static List<String> getCodeElements(TokenOrListParam tokenList, TokenSystem system) {
        return getCodeElements(tokenList, system.getUri());
    }

    public static List<String> getCodeElements(TokenOrListParam tokenList, UriDt system) {
        List<String> result = new ArrayList<>();
        for (BaseCodingDt coding: tokenList.getListAsCodings()){
            if (Objects.equals(coding.getSystemElement(), system)) {
                result.add(coding.getCodeElement().getValue());
            }
        }
        return result;
    }
}
