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

/**
 * Instances of this class are returned when querying an EHR server for
 * observation information.
 *
 * @author Blair Butterworth
 */
public class ObservationBundle extends QueryBundle
{
    private List<ObservationResult> resultSet;

    public ObservationBundle() {
        resultSet = new ArrayList<>();
    }

    public ObservationBundle(List<ObservationResult> resultSet) {
        this.resultSet = resultSet;
    }

    public List<ObservationResult> getResultSet() {
        return resultSet;
    }
}
