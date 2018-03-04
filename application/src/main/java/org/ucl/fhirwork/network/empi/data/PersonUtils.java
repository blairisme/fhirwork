/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.empi.data;

import org.ucl.fhirwork.network.empi.exception.IdentifierMissingException;

import java.util.*;

/**
 * Instances of this class provide helper functions for dealing with
 * {@link Person} objects.
 *
 * @author Blair Butterworth
 */
public class PersonUtils
{
    public static Identifier getIdentifier(Person person, String domain)
    {
        Identifier[] identifiers = person.getPersonIdentifiers();
        for (Identifier identifier: identifiers){
            if (Objects.equals(identifier.getIdentifierDomain().getIdentifierDomainName(), domain)){
                return identifier;
            }
        }
        throw new IdentifierMissingException(person, domain);
    }
}
