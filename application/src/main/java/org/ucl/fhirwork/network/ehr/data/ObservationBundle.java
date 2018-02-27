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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Instances of this class are returned when querying an EHR server for
 * observation information.
 *
 * @author Blair Butterworth
 */
public class ObservationBundle extends QueryBundle
{
    //private List<ObservationResult> resultSet;
    private List<Map<String, String>> resultSet;

    public ObservationBundle() {
        resultSet = new ArrayList<>();
    }

    public ObservationBundle(List<Map<String, String>> resultSet) {
        this.resultSet = resultSet;
    }

    public List<Map<String, String>> getResultSet() {
        return resultSet;
    }
}
