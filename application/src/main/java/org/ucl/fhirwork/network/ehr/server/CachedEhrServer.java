/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.ehr.server;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.lang3.tuple.Pair;
import org.ucl.fhirwork.common.network.Rest.RestException;
import org.ucl.fhirwork.common.network.exception.ResourceMissingException;
import org.ucl.fhirwork.configuration.ConfigService;
import org.ucl.fhirwork.configuration.data.CacheConfig;
import org.ucl.fhirwork.configuration.data.ConfigType;
import org.ucl.fhirwork.network.ehr.data.HealthRecord;
import org.ucl.fhirwork.network.ehr.data.QueryBundle;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

/**
 * Instances of this class act as a proxy to a {@link BasicEhrServer} delegate,
 * caching the results of calls to this class. Subsequent calls with identical
 * parameters will be provided cached results, improving performance and
 * lessening the load on the EHR server.
 *
 * @author Blair Butterworth
 */
public class CachedEhrServer implements EhrServer
{
    private EhrServer delegate;
    private ConfigService configService;
    private Cache<String, QueryBundle> queryCache;
    private Cache<Pair<String, String>, HealthRecord> recordCache;

    @Inject
    public CachedEhrServer(BasicEhrServer delegate, ConfigService configService) {
        this.delegate = delegate;
        this.configService = configService;
        this.configService.addObserver(this::resetCache);
    }

    @Override
    public void setConnectionDetails(String address, String username, String password) {
        delegate.setConnectionDetails(address, username, password);
    }

    @Override
    public HealthRecord getHealthRecord(String id, String namespace) throws RestException, ResourceMissingException {
        initializeCache();
        Pair<String, String> key = Pair.of(id, namespace);
        HealthRecord result = recordCache.getIfPresent(key);
        if (result == null){
            result = delegate.getHealthRecord(id, namespace);
            recordCache.put(key, result);
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends QueryBundle> T query(String query, Class<T> type) throws RestException {
        initializeCache();
        QueryBundle result = queryCache.getIfPresent(query);
        if (result == null){
            result = delegate.query(query, type);
            queryCache.put(query, result);
        }
        return (T)result;
    }

    private void initializeCache() {
        initializeQueryCache();
        initializeRecordCache();
    }

    private void initializeQueryCache() {
        if (queryCache == null) {
            CacheConfig cacheConfig = configService.getConfig(ConfigType.Cache);
            queryCache = Caffeine.newBuilder()
                .expireAfterWrite(cacheConfig.getEhrCacheExpiry(), TimeUnit.MINUTES)
                .maximumSize(cacheConfig.getEhrCacheSize())
                .build();
        }
    }

    private void initializeRecordCache() {
        if (recordCache == null) {
            CacheConfig cacheConfig = configService.getConfig(ConfigType.Cache);
            recordCache = Caffeine.newBuilder()
                .expireAfterWrite(cacheConfig.getEhrCacheExpiry(), TimeUnit.MINUTES)
                .maximumSize(cacheConfig.getEhrCacheSize())
                .build();
        }
    }

    private void resetCache() {
        resetQueryCache();
        resetRecordCache();
    }

    private void resetQueryCache() {
        if (queryCache != null) {
            queryCache.invalidateAll();
            queryCache = null;
        }
    }

    private void resetRecordCache() {
        if (recordCache != null) {
            recordCache.invalidateAll();
            recordCache = null;
        }
    }
}
