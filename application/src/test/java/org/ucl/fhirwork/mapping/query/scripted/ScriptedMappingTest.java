/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping.query.scripted;

import ca.uhn.fhir.model.dstu2.composite.QuantityDt;
import ca.uhn.fhir.model.dstu2.resource.Observation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ucl.fhirwork.common.resources.ResourceUtils;
import org.ucl.fhirwork.configuration.data.ScriptedMappingConfig;
import org.ucl.fhirwork.mapping.data.ObservationFactory;
import org.ucl.fhirwork.network.ehr.data.ObservationBundle;

import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ScriptedMappingTest
{
    private ScriptMapping scriptedMapping;
    private ScriptedMappingConfig configuration;

    @Before
    public void setup() throws Exception
    {
        File scriptFile = ResourceUtils.getResource("mapping/script.js");
        String script = FileUtils.readFileToString(scriptFile, StandardCharsets.UTF_8);
        configuration = new ScriptedMappingConfig("1234-5", script);

        scriptedMapping = new ScriptMapping(new ObservationFactory());
        scriptedMapping.setConfiguration(configuration);
    }

    @Test
    public void getQueryTest()
    {
        String actual = scriptedMapping.getQuery("123");
        String expected = "select " +
            "skeletal_age/data[at0001]/origin/value as date, " +
            "skeletal_age/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value/value as value " +
            "from EHR [ehr_id/value='123'] " +
            "contains COMPOSITION c " +
            "contains OBSERVATION skeletal_age[openEHR-EHR-OBSERVATION.skeletal_age.v0] ";
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void getObservationsTest()
    {
        Map<String, String> result1 = new HashMap<>();
        result1.put("date", "2010-04-10T00:00:00");
        result1.put("value", "P6Y");

        Map<String, String> result2 = new HashMap<>();
        result2.put("date", "2011-04-10T00:00:00");
        result2.put("value", "P7Y");

        List<Map<String, String>> resultSet = new ArrayList<>();
        resultSet.add(result1);
        resultSet.add(result2);

        ObservationBundle observationBundle = new ObservationBundle(resultSet);

        List<Observation> observations = scriptedMapping.getObservations("1234-5", "123", observationBundle);
        Assert.assertEquals(2, observations.size());

        Observation observation1 = observations.get(0);
        QuantityDt quantity1 = (QuantityDt)observation1.getValue();
        Assert.assertEquals(BigDecimal.valueOf(72.0), quantity1.getValue());
        Assert.assertEquals("Months", quantity1.getUnit());

        Observation observation2 = observations.get(1);
        QuantityDt quantity2 = (QuantityDt)observation2.getValue();
        Assert.assertEquals(BigDecimal.valueOf(84.0), quantity2.getValue());
        Assert.assertEquals("Months", quantity2.getUnit());
    }

    @Test
    @Ignore
    public void speedTest()
    {
        Map<String, String> result1 = new HashMap<>();
        result1.put("date", "2010-04-10T00:00:00");
        result1.put("value", "P6Y");

        Map<String, String> result2 = new HashMap<>();
        result2.put("date", "2011-04-10T00:00:00");
        result2.put("value", "P7Y");

        List<Map<String, String>> resultSet = new ArrayList<>();
        resultSet.add(result1);
        resultSet.add(result2);

        ObservationBundle observationBundle = new ObservationBundle(resultSet);

        StopWatch stopWatch = new StopWatch();
        int counter = 0;

        while (counter++ < 20) {
            stopWatch.reset();
            stopWatch.start();

            ScriptMapping scriptedMapping = new ScriptMapping(new ObservationFactory());
            scriptedMapping.setConfiguration(configuration);
            scriptedMapping.getObservations("1234-5", "123", observationBundle);

            stopWatch.stop();
            System.out.println(stopWatch.getTime());
        }
    }
}
