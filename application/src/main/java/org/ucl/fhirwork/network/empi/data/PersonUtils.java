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

import org.ucl.fhirwork.common.network.exception.IdentifierMissingException;

import java.util.*;

/**
 * Instances of this class provide helper functions for dealing with
 * {@link Person} objects.
 *
 * @author Blair Butterworth
 */
public class PersonUtils
{
    private PersonUtils(){
    }

    public static Identifier getIdentifier(Person person, String domain)
    {
        Identifier result = findIdentifier(person, domain);
        if (result == null) {
            throw new IdentifierMissingException("Person", person.getPersonId(), domain);
        }
        return result;
    }

    public static boolean hasIdentifier(Person person, String domain)
    {
        Identifier result = findIdentifier(person, domain);
        return result != null;
    }

    private static Identifier findIdentifier(Person person, String domain)
    {
        Identifier[] identifiers = person.getPersonIdentifiers();
        for (Identifier identifier: identifiers){
            if (Objects.equals(identifier.getIdentifierDomain().getIdentifierDomainName(), domain)){
                return identifier;
            }
        }
        return null;
    }
}
