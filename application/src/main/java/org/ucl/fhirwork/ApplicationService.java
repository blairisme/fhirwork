/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork;

import org.ucl.fhirwork.common.framework.ExecutionException;
import org.ucl.fhirwork.common.framework.Executor;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.configuration.ConfigService;
import org.ucl.fhirwork.mapping.MappingService;
import org.ucl.fhirwork.network.NetworkService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Instances of this class facilitate the execution of {@link Operation}s.
 *
 * @author Blair Butterworth
 */
@Singleton
public class ApplicationService
{
    private ConfigService configuration;
    private MappingService mappingService;
    private NetworkService networkService;

    @Inject
    public ApplicationService(
            ConfigService configuration,
            MappingService mappingService,
            NetworkService networkService)
    {
        this.configuration = configuration;
        this.mappingService = mappingService;
        this.networkService = networkService;
    }

    public Object execute(Operation operation) throws ExecutionException
    {
        Executor executor = mappingService.getExecutor(operation);
        return executor.invoke();
    }
}
