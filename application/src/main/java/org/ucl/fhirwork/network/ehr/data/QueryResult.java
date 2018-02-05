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

public class QueryResult
{
    private String date;
    private String magnitude;
    private String unit;

    public QueryResult() {
    }

    public QueryResult(String date, String magnitude, String unit) {
        this.date = date;
        this.magnitude = magnitude;
        this.unit = unit;
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
