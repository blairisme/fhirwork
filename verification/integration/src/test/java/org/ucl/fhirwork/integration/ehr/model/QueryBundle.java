package org.ucl.fhirwork.integration.ehr.model;

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
