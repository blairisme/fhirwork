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

import org.ucl.fhirwork.common.network.Rest.RestException;
import org.ucl.fhirwork.common.network.exception.ResourceMissingException;
import org.ucl.fhirwork.configuration.ConfigService;
import org.ucl.fhirwork.configuration.data.CacheConfig;
import org.ucl.fhirwork.configuration.data.ConfigType;
import org.ucl.fhirwork.network.ehr.data.HealthRecord;
import org.ucl.fhirwork.network.ehr.data.QueryBundle;

import javax.inject.Inject;

/**
 * Instances of this class act as a proxy to either an {@link BasicEhrServer}
 * or an {@link CachedEhrServer}, depending on whether the user has enabled
 * EHR caching.
 *
 * @author Blair Butterworth
 */
public class ConfigEhrServer implements EhrServer
{
    private EhrServer delegate;
    private BasicEhrServer basicServer;
    private CachedEhrServer cachedServer;
    private ConfigService configService;

    @Inject
    public ConfigEhrServer(ConfigService configService, BasicEhrServer basicServer, CachedEhrServer cachedServer) {
        this.basicServer = basicServer;
        this.cachedServer = cachedServer;
        this.configService = configService;
        this.configService.addObserver(this::setDelegate);
    }

    @Override
    public void setConnectionDetails(String address, String username, String password) {
        basicServer.setConnectionDetails(address, username, password);
        cachedServer.setConnectionDetails(address, username, password);
    }

    @Override
    public HealthRecord getHealthRecord(String id, String namespace) throws RestException, ResourceMissingException {
        return getDelegate().getHealthRecord(id, namespace);
    }

    @Override
    public <T extends QueryBundle> T query(String query, Class<T> type) throws RestException {
        return getDelegate().query(query, type);
    }

    private EhrServer getDelegate() {
        if (delegate == null) {
            setDelegate();
        }
        return delegate;
    }

    private void setDelegate() {
        CacheConfig cacheConfig = configService.getConfig(ConfigType.Cache);
        delegate = cacheConfig.isEhrCacheEnabled() ? cachedServer : basicServer;
    }
}
