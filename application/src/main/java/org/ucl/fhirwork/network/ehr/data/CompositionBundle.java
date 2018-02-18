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

public class CompositionBundle
{
    private List<CompositionResult> resultSet;

    public CompositionBundle()
    {
        resultSet = new ArrayList<>();
    }

    public CompositionBundle(List<CompositionResult> resultSet) {
        this.resultSet = resultSet;
    }

    public List<CompositionResult> getResultSet() {
        return resultSet;
    }
}
