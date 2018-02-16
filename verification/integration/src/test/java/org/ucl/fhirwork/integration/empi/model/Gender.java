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