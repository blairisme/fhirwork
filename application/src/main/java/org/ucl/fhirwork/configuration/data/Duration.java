/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.configuration.data;

import java.util.concurrent.TimeUnit;

/**
 * Instances of this class represent a period of time.
 *
 * @author Blair Butterworth
 */
public class Duration
{
    private int value;
    private String unit;

    public Duration(int value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    public int getValue() {
        return value;
    }

    public TimeUnit getUnit() {
        switch (unit.toLowerCase()) {
            case "seconds" : return TimeUnit.SECONDS;
            case "minutes" : return TimeUnit.MINUTES;
            case "days" : return TimeUnit.DAYS;
            case "hours" : return TimeUnit.HOURS;
            default : throw new IllegalArgumentException("Illegal time unit: " + unit);
        }
    }
}
