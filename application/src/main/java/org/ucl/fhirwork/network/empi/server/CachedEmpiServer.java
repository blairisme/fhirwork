/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.empi.server;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.ucl.fhirwork.common.network.Rest.RestException;
import org.ucl.fhirwork.common.network.exception.AmbiguousResultException;
import org.ucl.fhirwork.common.network.exception.ResourceMissingException;
import org.ucl.fhirwork.configuration.ConfigService;
import org.ucl.fhirwork.configuration.data.CacheConfig;
import org.ucl.fhirwork.configuration.data.ConfigType;
import org.ucl.fhirwork.network.empi.data.InternalIdentifier;
import org.ucl.fhirwork.network.empi.data.Person;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Instances of this class act as a proxy to a {@link BasicEmpiServer} delegate,
 * caching the results of calls to this class. Subsequent calls with identical
 * parameters will be provided cached results, improving performance and
 * lessening the load on the EMPI server.
 *
 * @author Blair Butterworth
 */
public class CachedEmpiServer implements EmpiServer
{
    private EmpiServer delegate;
    private ConfigService configService;
    private Cache<InternalIdentifier, Person> recentCache;
    private Cache<Person, Collection<Person>> searchCache;

    @Inject
    public CachedEmpiServer(BasicEmpiServer delegate, ConfigService configService) {
        this.delegate = delegate;
        this.configService = configService;
        this.configService.addObserver(this::resetCache);
    }

    private void initializeCache() {
        if (recentCache == null || searchCache == null) {
            CacheConfig cacheConfig = configService.getConfig(ConfigType.Cache);
            recentCache = Caffeine.newBuilder()
                .expireAfterWrite(cacheConfig.getEmpiCacheExpiry(), TimeUnit.MINUTES)
                .maximumSize(cacheConfig.getEmpiCacheSize())
                .build();
            searchCache = Caffeine.newBuilder()
                .expireAfterWrite(cacheConfig.getEmpiCacheExpiry(), TimeUnit.MINUTES)
                .maximumSize(100)
                .build();
        }
    }

    private void resetCache() {
        if (recentCache != null) {
            recentCache.invalidateAll();
            recentCache = null;
        }
        if (searchCache != null) {
            searchCache.invalidateAll();
            searchCache = null;
        }
    }

    @Override
    public void setConnectionDetails(String address, String username, String password) {
        delegate.setConnectionDetails(address, username, password);
    }

    @Override
    public Person addPerson(Person person) throws RestException {
        initializeCache();
        Person result = delegate.addPerson(person);
        recentCache.put(result.getInternalIdentifier(), result);
        searchCache.invalidateAll();
        return result;
    }

    @Override
    public Person findPerson(Person template) throws RestException, ResourceMissingException, AmbiguousResultException {
        return delegate.findPerson(template);
    }

    @Override
    public Collection<Person> findPersons(Person template) throws RestException {
        initializeCache();
        Collection<Person> result = searchCache.getIfPresent(template);
        if (result == null){
            result = delegate.findPersons(template);
            searchCache.put(template, result);
        }
        return result;
    }

    @Override
    public Person loadPerson(InternalIdentifier identifier) throws RestException, ResourceMissingException {
        initializeCache();
        Person result = recentCache.getIfPresent(identifier);
        if (result == null){
            result = delegate.loadPerson(identifier);
            recentCache.put(identifier, result);
        }
        return result;
    }

    @Override
    public List<Person> loadAllPersons(int index, int count) throws RestException {
        return delegate.loadAllPersons(index, count);
    }

    @Override
    public void removePerson(InternalIdentifier identifier) throws RestException {
        initializeCache();
        delegate.removePerson(identifier);
        recentCache.invalidate(identifier);
        searchCache.invalidateAll();
    }

    @Override
    public Person updatePerson(Person person) throws RestException, ResourceMissingException {
        initializeCache();
        Person result = delegate.updatePerson(person);
        recentCache.put(result.getInternalIdentifier(), result);
        searchCache.invalidateAll();
        return result;
    }
}
