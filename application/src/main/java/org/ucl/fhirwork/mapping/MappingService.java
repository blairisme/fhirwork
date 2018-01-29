/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping;

import org.ucl.fhirwork.common.framework.Executor;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.mapping.executor.CreatePatientExecutor;
import org.ucl.fhirwork.network.fhir.operations.CreatePatientOperation;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.HashMap;
import java.util.Map;

public class MappingService
{
    private Map<Class<?>, Provider<? extends Executor>> executorFactories;

    @Inject
    public MappingService(Provider<CreatePatientExecutor> createPatientFactory)
    {
        this.executorFactories = new HashMap<>();
        this.executorFactories.put(CreatePatientOperation.class, createPatientFactory);
    }

    public Executor getExecutor(Operation operation)
    {
        Provider<? extends Executor> provider = executorFactories.get(operation.getClass());
        if (provider != null)
        {
            Executor executor = provider.get();
            executor.setOperation(operation);
            return executor;
        }
        throw new UnsupportedOperationException();
    }
}