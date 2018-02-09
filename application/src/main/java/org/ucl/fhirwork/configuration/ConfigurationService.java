/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationService
{
    private Map<Configuration, Object> values;

    public ConfigurationService()
    {
        values = new HashMap<>();

        values.put(Configuration.Ehr, new NetworkConfiguration(
                System.getProperty("network.ehr.address", "http://localhost:8888/rest/v1"),
                System.getProperty("network.ehr.username", "guest"),
                System.getProperty("network.ehr.password", "guest")));

        values.put(Configuration.Empi, new NetworkConfiguration(
                System.getProperty("network.empi.address", "http://localhost:8080"),
                System.getProperty("network.empi.username", "admin"),
                System.getProperty("network.empi.password", "admin")));

        //values.put(Configuration.Ehr, new NetworkConfiguration("https://test.operon.systems/rest/v1", "oprn_jarrod", "ZayFYCiO644"));
        //values.put(Configuration.Ehr, new NetworkConfiguration("http://localhost:8888/rest/v1", "guest", "guest"));

        MappingPath path = new MappingPath(
                "data[at0002]/origin/value",
                "data[at0002]/events[at0003]/data[at0001]/items[at0004]/value/magnitude",
                "data[at0002]/events[at0003]/data[at0001]/items[at0004]/value/units");
        MappingSpecification mappingSpecification = new MappingSpecification(
                path, "openEHR-EHR-OBSERVATION.body_weight.v1", "body_weight", "3141-9");
        MappingConfiguration mappingConfiguration = new MappingConfiguration(Arrays.asList(mappingSpecification));

        values.put(Configuration.Mapping, mappingConfiguration);
    }

    @SuppressWarnings("unchecked")
    public <T> T getConfiguration(Configuration key)
    {
        T result = (T)values.get(key);
        if (result != null){
            return result;
        }
        throw new IllegalStateException();
    }

}
