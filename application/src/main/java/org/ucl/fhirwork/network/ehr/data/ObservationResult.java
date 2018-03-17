/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.ehr.data;

/**
 * Instances of this class contain represent the information contained in a
 * single EHR observation entry.
 *
 * @author Blair Butterworth
 */
public class ObservationResult
{
    private String date;
    private String magnitude;
    private String unit;

    public ObservationResult() {
    }

    public String getDate() {
        return date;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getUnit() {
        return unit;
    }
}
