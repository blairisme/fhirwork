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
 * Instances of this error are thrown when a requested resource cannot be
 * found.
 *
 * @author Blair Butterworth
 */
public class ResourceMissingException extends NetworkException
{
    private String type;
    private String identifier;

    public ResourceMissingException(String type, String identifier) {
        super(type + " missing: " + identifier);
        this.type = type;
        this.identifier = identifier;
    }

    public String getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }
}
