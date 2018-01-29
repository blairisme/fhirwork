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
        this("", "", "", "");
    }

    public Gender(String genderCd, String genderCode, String genderName, String genderDescription) {
        this.genderCd = genderCd;
        this.genderCode = genderCode;
        this.genderName = genderName;
        this.genderDescription = genderDescription;
    }

    public static Gender fromName(String name)
    {
        if (name.equalsIgnoreCase("male")){
            return new Gender("2", "M", "male", "male");
        }
        else if (name.equalsIgnoreCase("female")){
            return new Gender("1", "F", "female", "female");
        }
        else {
            throw new UnsupportedOperationException();
        }
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