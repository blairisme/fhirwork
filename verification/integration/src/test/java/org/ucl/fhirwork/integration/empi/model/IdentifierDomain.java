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
    private String namespaceIdentifier;

    public IdentifierDomain() {
        this(null);
    }

    public IdentifierDomain(String namespaceIdentifier) {
        this.namespaceIdentifier = namespaceIdentifier;
    }

    public String getNamespaceIdentifier() {
        return namespaceIdentifier;
    }

    public void setNamespaceIdentifier(String namespaceIdentifier) {
        this.namespaceIdentifier = namespaceIdentifier;
    }
}
