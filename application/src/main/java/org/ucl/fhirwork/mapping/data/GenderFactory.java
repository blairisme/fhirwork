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

import org.ucl.fhirwork.network.empi.data.Gender;

/**
 * Instances of this class create {@link Gender} instances given their textual
 * representation.
 *
 * @author Blair Butterworth
 */
public class GenderFactory
{
    public Gender fromName(String name)
    {
        /*
        if (name.equalsIgnoreCase("male")){
            return new Gender("2", "M", "male", "male");
        }
        else if (name.equalsIgnoreCase("female")){
            return new Gender("1", "F", "female", "female");
        }
        */
        throw new UnsupportedOperationException();
    }
}
