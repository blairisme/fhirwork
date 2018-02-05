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
        values.put(Configuration.Empi, new NetworkConfiguration("http://localhost:8080", "admin", "admin"));
        values.put(Configuration.Ehr, new NetworkConfiguration("https://test.operon.systems/rest/v1", "oprn_jarrod", "ZayFYCiO644"));
        //values.put(Configuration.Ehr, new NetworkConfiguration("http://localhost:8888/rest/v1", "guest", "guest"));
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
