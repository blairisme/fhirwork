/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.common.network.exception;

/**
 * Instances of this error are thrown when performing an operation where the
 * presence of a global identifier (as opposed to an internal identifier) is
 * mandatory.
 *
 * @author Blair Butterworth
 */
public class IdentifierMissingException extends NetworkException
{
    public IdentifierMissingException(String type, String id, String domain)
    {
        super(type + " with internal id " + id + " has no external identifier belonging to " + domain);
    }
}
