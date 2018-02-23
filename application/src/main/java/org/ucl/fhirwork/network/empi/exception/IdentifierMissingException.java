/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.empi.exception;

import org.ucl.fhirwork.network.empi.data.Person;

public class IdentifierMissingException extends RuntimeException
{
    public IdentifierMissingException(Person person, String domain)
    {
        super("Person " + person.getPersonId() + " has no identifier belonging to " + domain);
    }
}
