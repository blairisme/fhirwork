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

import java.util.HashMap;
import java.util.Map;

public class ConfigurationService
{
    private Map<Configuration, Object> values;

    public ConfigurationService()
    {
        values = new HashMap<>();
        values.put(Configuration.Empi, new NetworkConfiguration("http://localhost:8080", "admin", "admin"));
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
