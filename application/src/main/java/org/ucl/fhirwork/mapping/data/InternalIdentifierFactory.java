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

import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.rest.param.ReferenceParam;
import org.ucl.fhirwork.network.empi.data.InternalIdentifier;

/**
 * Instances of this class create {@link InternalIdentifier InternalIdentifers}
 * from the identifier contained in {@link ReferenceParam} objects.
 *
 * @author Blair Butterworth
 */
public class InternalIdentifierFactory
{
    public InternalIdentifier fromId(IdDt identifier){
        return new InternalIdentifier(identifier.getIdPart());
    }

    public InternalIdentifier fromReference(ReferenceParam identifier){
        String value = identifier.getValue();

        if (value.contains("/")){
            return new InternalIdentifier(value.split("/")[1]);
        }
        if (value.contains("=")){
            return new InternalIdentifier(value.split("=")[1]);
        }
        return new InternalIdentifier(value);
    }
}

