package org.ucl.fhirwork.mapping.query;

import org.junit.Test;

import java.util.Arrays;

public class QueryBuilderTest
{
    @Test
    public void getQueryTest()
    {
        QueryBuilder queryBuilder = new QueryBuilder();
        String query = queryBuilder.getQuery("123", Arrays.asList("3141-9"));
        query.length();
    }
}
