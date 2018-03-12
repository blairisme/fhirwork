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

import javax.annotation.concurrent.Immutable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Instances of this class represent a EMPI patients gender.
 *
 * <pre>{@code
 *
 * <gender>
 *     <genderCd>1</genderCd>
 *     <genderCode>F</genderCode>
 *     <genderDescription>Female</genderDescription>
 *     <genderName>Female</genderName>
 * </gender>
 *
 * }</pre>
 *
 * @author Blair Butterworth
 */
@Immutable
@XmlRootElement(name = "gender")
@SuppressWarnings("unused")
public final class Gender
{
    private String genderCd;
    private String genderCode;
    private String genderName;
    private String genderDescription;

    public Gender()
    {
    }

    public Gender(
        String genderCd,
        String genderCode,
        String genderName,
        String genderDescription)
    {
        this.genderCd = genderCd;
        this.genderCode = genderCode;
        this.genderName = genderName;
        this.genderDescription = genderDescription;
    }

    public String getGenderCd() {
        return genderCd;
    }

    public void setGenderCd(String genderCd) {
        this.genderCd = genderCd;
    }

    public String getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getGenderDescription() {
        return genderDescription;
    }

    public void setGenderDescription(String genderDescription) {
        this.genderDescription = genderDescription;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;

        if (object instanceof Gender) {
            Gender other = (Gender)object;
            return new EqualsBuilder()
                .append(this.genderCd, other.genderCd)
                .append(this.genderCode, other.genderCode)
                .append(this.genderName, other.genderName)
                .append(this.genderDescription, other.genderDescription)
                .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(genderCd)
            .append(genderCode)
            .append(genderName)
            .append(genderDescription)
            .toHashCode();
    }
}