/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.configuration.data;

/**
 * Instances of this class represent a container for all persisted cache
 * configuration data.
 *
 * @author Xiaolong Chen
 * @author Blair Butterworth
 */
public class CacheConfig {
    private int empiCacheSize;
    private int empiCacheExpiry;
    private boolean empiCacheEnabled;

    public CacheConfig(int empiCacheSize, int empiCacheExpiry, boolean empiCacheEnabled) {
        this.empiCacheSize = empiCacheSize;
        this.empiCacheExpiry = empiCacheExpiry;
        this.empiCacheEnabled = empiCacheEnabled;
    }

    public int getEmpiCacheSize() {
        return empiCacheSize;
    }

    public int getEmpiCacheExpiry() {
        return empiCacheExpiry;
    }

    public boolean isEmpiCacheEnabled() {
        return empiCacheEnabled;
    }
}
