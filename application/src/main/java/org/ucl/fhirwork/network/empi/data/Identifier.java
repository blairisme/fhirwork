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

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "personIdentifier")
@SuppressWarnings("unused")
public class Identifier
{
    private String identifier;
    private IdentifierDomain identifierDomain;

    public Identifier() {
        this(null, null);
    }

    public Identifier(String identifier, IdentifierDomain identifierDomain) {
        this.identifier = identifier;
        this.identifierDomain = identifierDomain;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public IdentifierDomain getIdentifierDomain() {
        return identifierDomain;
    }

    public void setIdentifierDomain(IdentifierDomain identifierDomain) {
        this.identifierDomain = identifierDomain;
    }
}
