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

@XmlRootElement(name = "identifierDomain")
@SuppressWarnings("unused")
public class IdentifierDomain
{
    private String identifierDomainName;
    private String namespaceIdentifier;
    private String universalIdentifier;
    private String universalIdentifierTypeCode;

    public IdentifierDomain() {
    }

    public IdentifierDomain(
        String identifierDomainName,
        String namespaceIdentifier,
        String universalIdentifier,
        String universalIdentifierTypeCode)
    {
        this.identifierDomainName = identifierDomainName;
        this.namespaceIdentifier = namespaceIdentifier;
        this.universalIdentifier = universalIdentifier;
        this.universalIdentifierTypeCode = universalIdentifierTypeCode;
    }

    public String getNamespaceIdentifier() {
        return namespaceIdentifier;
    }

    public void setNamespaceIdentifier(String namespaceIdentifier) {
        this.namespaceIdentifier = namespaceIdentifier;
    }

    public static IdentifierDomain fromText(String text) {
        if (text.equalsIgnoreCase("SSN")) {
            return new IdentifierDomain("SSN", "2.16.840.1.113883.4.1", "2.16.840.1.113883.4.1", "SSN");
        }
        if (text.equalsIgnoreCase("uk.nhs.nhs_number")){
            return new IdentifierDomain("uk.nhs.nhs_number", "uk.nhs.nhs_number", "uk.nhs.nhs_number", "uk.nhs.nhs_number");
        }
        throw new UnsupportedOperationException();
    }
}
