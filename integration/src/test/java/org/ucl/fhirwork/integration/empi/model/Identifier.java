package org.ucl.fhirwork.integration.empi.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "identifier")
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
