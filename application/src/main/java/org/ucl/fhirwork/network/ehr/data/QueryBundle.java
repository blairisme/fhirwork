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

public class QueryBundle
{
    private List<QueryResult> resultSet;

    public QueryBundle()
    {
        resultSet = new ArrayList<>();
    }

    public QueryBundle(List<QueryResult> resultSet) {
        this.resultSet = resultSet;
    }

    public List<QueryResult> getResultSet() {
        return resultSet;
    }
}
