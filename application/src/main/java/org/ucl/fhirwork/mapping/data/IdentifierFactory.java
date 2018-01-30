/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping.data;

import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.primitive.IdDt;
import org.ucl.fhirwork.network.empi.data.Identifier;
import org.ucl.fhirwork.network.empi.data.IdentifierDomain;

/**
 * Instances of this class construct {@link Identifier} objects, usually from
 * FHIR objects such as {@link IdDt}.
 *
 * @author Blair Butterworth
 */
public class IdentifierFactory
{
    public Identifier fromId(IdentifierDt id)
    {
        IdentifierDomain identifierDomain = new IdentifierDomain();
        identifierDomain.setIdentifierDomainName(id.getSystem());

        Identifier result = new Identifier();
        result.setIdentifier(id.getValue());
        result.setIdentifierDomain(identifierDomain);

        return result;
    }

    /*
    public Identifier fromId(IdDt id)
    {
        IdentifierDomain identifierDomain = new IdentifierDomain();
        identifierDomain.setIdentifierDomainName("FHIR");

        Identifier result = new Identifier();
        result.setIdentifier(id.getIdPart());
        result.setIdentifierDomain(identifierDomain);

        return result;
    }
    */
}
