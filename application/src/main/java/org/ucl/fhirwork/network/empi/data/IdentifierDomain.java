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
