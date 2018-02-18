/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.empi.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "identifier")
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

    public static Identifier fromToken(String token)
    {
        return new Identifier(token, new IdentifierDomain("SSN"));
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
