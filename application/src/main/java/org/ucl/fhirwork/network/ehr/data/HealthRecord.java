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

public class HealthRecord
{
    private String ehrId;

    public HealthRecord(String ehrId) {
        this.ehrId = ehrId;
    }

    public String getEhrId() {
        return ehrId;
    }
}
