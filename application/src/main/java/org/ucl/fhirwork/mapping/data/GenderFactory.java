/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping.data;

import ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum;
import org.ucl.fhirwork.network.empi.data.Gender;

/**
 * Instances of this class create {@link Gender} instances given their textual
 * representation.
 *
 * @author Blair Butterworth
 */
public class GenderFactory
{
    public Gender fromEnum(AdministrativeGenderEnum gender)
    {
        switch (gender){
            case FEMALE: return new Gender("1", "F", "Female", "Female");
            case MALE : return new Gender("2", "M", "Male", "Male");
            case OTHER : return new Gender("3", "O", "Other", "Other");
            case UNKNOWN : return new Gender("4", "U", "Unknown", "Unknown");
            default: throw new IllegalArgumentException("Unsupported gender: " + gender);
        }
    }

    public AdministrativeGenderEnum fromGender(Gender gender)
    {
        switch (gender.getGenderCode()){
            case "F": return AdministrativeGenderEnum.FEMALE;
            case "M": return AdministrativeGenderEnum.MALE;
            case "O": return AdministrativeGenderEnum.OTHER;
            case "U": return AdministrativeGenderEnum.UNKNOWN;
            default: throw new IllegalArgumentException("Unsupported gender: " + gender);
        }
    }
}
