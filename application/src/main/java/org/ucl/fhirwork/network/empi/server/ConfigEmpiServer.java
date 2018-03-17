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

/**
 * Instances of this class act as a proxy to either an {@link BasicEmpiServer}
 * or an {@link CachedEmpiServer}, depending on whether the user has enabled
 * EMPI caching.
 *
 * @author Blair Butterworth
 */
public class ConfigEmpiServer implements EmpiServer
{
    private EmpiServer delegate;
    private BasicEmpiServer basicServer;
    private CachedEmpiServer cacheServer;
    private ConfigService configService;

    @Inject
    public ConfigEmpiServer(ConfigService configService, BasicEmpiServer basicServer, CachedEmpiServer cacheServer) {
        this.basicServer = basicServer;
        this.cacheServer = cacheServer;
        this.configService = configService;
        this.configService.addObserver(this::setDelegate);
    }

    @Override
    public void setConnectionDetails(String address, String username, String password) {
        basicServer.setConnectionDetails(address, username, password);
        cacheServer.setConnectionDetails(address, username, password);
    }

    @Override
    public Person addPerson(Person person) throws RestException {
        return getDelegate().addPerson(person);
    }

    @Override
    public Person findPerson(Person template) throws RestException, ResourceMissingException, AmbiguousResultException {
        return getDelegate().findPerson(template);
    }

    @Override
    public Collection<Person> findPersons(Person template) throws RestException {
        return getDelegate().findPersons(template);
    }

    @Override
    public Person loadPerson(InternalIdentifier identifier) throws RestException, ResourceMissingException {
        return getDelegate().loadPerson(identifier);
    }

    @Override
    public List<Person> loadAllPersons(int index, int count) throws RestException {
        return getDelegate().loadAllPersons(index, count);
    }

    @Override
    public void removePerson(InternalIdentifier identifier) throws RestException {
        getDelegate().removePerson(identifier);
    }

    @Override
    public Person updatePerson(Person person) throws RestException, ResourceMissingException {
        return getDelegate().updatePerson(person);
    }

    private EmpiServer getDelegate() {
        if (delegate == null) {
            setDelegate();
        }
        return delegate;
    }

    private void setDelegate() {
        CacheConfig cacheConfig = configService.getConfig(ConfigType.Cache);
        delegate = cacheConfig.isEmpiCacheEnabled() ? cacheServer : basicServer;
    }
}
