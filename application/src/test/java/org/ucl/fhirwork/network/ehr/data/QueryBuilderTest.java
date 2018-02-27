package org.ucl.fhirwork.network.ehr.data;

import org.junit.Assert;
import org.junit.Test;

public class QueryBuilderTest
{
    @Test
    public void buildTest()
    {
        QueryBuilder query = new QueryBuilder();
        query.appendSelectStatement("body_weight", "data[at0002]/origin/value", "date");
        query.appendSelectStatement("body_weight", "data[at0002]/events[at0003]/data[at0001]/items[at0004]/value/magnitude", "magnitude");
        query.appendSelectStatement("body_weight", "data[at0002]/events[at0003]/data[at0001]/items[at0004]/value/units", "unit");
        query.appendFromStatement("EHR", "ehr_id/value='c831fe4d-0ce9-4a63-8bfa-2c51007f97e5'");
        query.appendContainsStatement("COMPOSITION", "c");
        query.appendContainsStatement("OBSERVATION", "body_weight", "openEHR-EHR-OBSERVATION.body_weight.v1");
        String actual = query.build();
        String expected = "select " +
                "body_weight/data[at0002]/origin/value as date, " +
                "body_weight/data[at0002]/events[at0003]/data[at0001]/items[at0004]/value/magnitude as magnitude, " +
                "body_weight/data[at0002]/events[at0003]/data[at0001]/items[at0004]/value/units as unit " +
                "from EHR[ehr_id/value='c831fe4d-0ce9-4a63-8bfa-2c51007f97e5'] " +
                "contains COMPOSITION c " +
                "contains OBSERVATION body_weight[openEHR-EHR-OBSERVATION.body_weight.v1] ";
        Assert.assertEquals(expected, actual);
    }
}
