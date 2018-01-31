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
@XmlRootElement(name = "identifier")
@SuppressWarnings("unused")
public class Gender
{
    private String genderCd;
    private String genderCode;
    private String genderName;
    private String genderDescription;

    public Gender()
    {
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
}