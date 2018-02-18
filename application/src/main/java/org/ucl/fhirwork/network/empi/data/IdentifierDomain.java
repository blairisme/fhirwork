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

/**
 * Instances of this class represent an EMPI identifier domain, a system for
 * patient identification. E.g., NHS numbers. The EMPI REST web service
 * specifies identifier domains using the following format.
 *
 * <pre>{@code
 *
 *  <identifierDomain>
 *      <identifierDomainId>13</identifierDomainId>
 *      <identifierDomainName>OpenMRS</identifierDomainName>
 *      <namespaceIdentifier>35a02490-5c20-11de-8ae4-001d60400e9e</namespaceIdentifier>
 *      <universalIdentifier>35a02490-5c20-11de-8ae4-001d60400e9e</universalIdentifier>
 *      <universalIdentifierTypeCode>OpenMRS</universalIdentifierTypeCode>
 *  </identifierDomain>
 *
 *}</pre>
 *
 * @author Blair Butterworth
 */
@XmlRootElement(name = "identifierDomain")
@SuppressWarnings("unused")
public class IdentifierDomain
{
    private String identifierDomainId;
    private String identifierDomainName;
    private String namespaceIdentifier;
    private String universalIdentifier;
    private String universalIdentifierTypeCode;

    public IdentifierDomain() {
    }

    public String getIdentifierDomainId() {
        return identifierDomainId;
    }

    public void setIdentifierDomainId(String identifierDomainId) {
        this.identifierDomainId = identifierDomainId;
    }

    public String getIdentifierDomainName() {
        return identifierDomainName;
    }

    public void setIdentifierDomainName(String identifierDomainName) {
        this.identifierDomainName = identifierDomainName;
    }

    public String getNamespaceIdentifier() {
        return namespaceIdentifier;
    }

    public void setNamespaceIdentifier(String namespaceIdentifier) {
        this.namespaceIdentifier = namespaceIdentifier;
    }

    public String getUniversalIdentifier() {
        return universalIdentifier;
    }

    public void setUniversalIdentifier(String universalIdentifier) {
        this.universalIdentifier = universalIdentifier;
    }

    public String getUniversalIdentifierTypeCode() {
        return universalIdentifierTypeCode;
    }

    public void setUniversalIdentifierTypeCode(String universalIdentifierTypeCode) {
        this.universalIdentifierTypeCode = universalIdentifierTypeCode;
    }
}