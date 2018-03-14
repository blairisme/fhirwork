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
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.TokenParam;
import org.apache.commons.lang3.Validate;

/**
 * Instances of this class create {@link TokenParam} instances, given their
 * textual representation.
 *
 * @author Blair Butterworth
 */
public class TokenParamFactory
{
    private TokenParamFactory() {
    }

    public static TokenParam fromText(String value) {
        new ReferenceParam();
        new IdentifierDt();

        String[] parameterSections = value.split("\\|");
        if (parameterSections.length == 2) {
            String system = parameterSections[0];
            String identifier = parameterSections[1];

            validate(system);
            validate(identifier);

            return new TokenParam(system, identifier);
        }
        throw new IllegalArgumentException(value);
    }

    private static void validate(String value) {
        if (value.contains(":text") ||
            value.contains(":not") ||
            value.contains(":above") ||
            value.contains(":below") ||
            value.contains(":in") ||
            value.contains(":not-in")) {
            throw new IllegalArgumentException(value);
        }
    }
}