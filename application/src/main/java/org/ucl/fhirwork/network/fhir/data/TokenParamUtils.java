package org.ucl.fhirwork.network.fhir.data;

import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.TokenParam;

public class TokenParamUtils
{
    // TODO: Extract properly - http://hl7.org/fhir/search.html#token
    public static TokenParam fromText(String value)
    {
        new ReferenceParam();
        new IdentifierDt();

        String[] parameterSections = value.split("\\|");
        if (parameterSections.length == 2)
        {
            String system = parameterSections[0];
            String identifier = parameterSections[1];
            return new TokenParam(system, identifier);
        }
        throw new IllegalArgumentException(value);
    }
}
