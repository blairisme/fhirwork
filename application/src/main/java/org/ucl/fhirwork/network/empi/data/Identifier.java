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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Instances of this class represent an EMPI identifier, used to identify
 * patients. Patients can have one or more identifiers. The EMPI system also
 * assigns an internal identifier which is not accessible through this class.
 * The EMPI REST web service specifies identifiers using the following format.
 *
 * <pre>{@code
 *
 * <personIdentifiers>
 *      <dateCreated>2017-07-19T21:49:41.729Z</dateCreated>
 *      <identifier>568749875445698798988873</identifier>
 *      <identifierDomain> ... </identifierDomain>
 *  </personIdentifiers>
 *
 *}</pre>
 *
 * @author Blair Butterworth
 */
@XmlRootElement(name = "personIdentifier")
@SuppressWarnings("unused")
public class Identifier
{
    private String identifier;
    private String dateCreated;
    private IdentifierDomain identifierDomain;

    public Identifier() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public IdentifierDomain getIdentifierDomain() {
        return identifierDomain;
    }

    public void setIdentifierDomain(IdentifierDomain identifierDomain) {
        this.identifierDomain = identifierDomain;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;

        if (object instanceof Identifier) {
            Identifier other = (Identifier)object;
            return new EqualsBuilder()
                .append(this.identifier, other.identifier)
                .append(this.dateCreated, other.dateCreated)
                .append(this.identifierDomain, other.identifierDomain)
                .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(identifier)
                .append(dateCreated)
                .append(identifierDomain)
                .toHashCode();
    }
}
