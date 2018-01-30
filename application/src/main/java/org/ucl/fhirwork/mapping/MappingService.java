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
import org.ucl.fhirwork.mapping.executor.ReadPatientExecutor;
import org.ucl.fhirwork.network.fhir.operations.CreatePatientOperation;
import org.ucl.fhirwork.network.fhir.operations.ReadPatientOperation;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.HashMap;
import java.util.Map;

/**
 * Instances of this class provide {@link Executor}s that perform the actions
 * appropriate for a given {@link Operation}.
 *
 * @author Blair Butterworth
 */
public class MappingService
{
    private Map<Class<?>, Provider<? extends Executor>> executorFactories;

    @Inject
    public MappingService(
            Provider<CreatePatientExecutor> createPatientProvider,
            Provider<ReadPatientExecutor> readPatientProvider)
    {
        this.executorFactories = new HashMap<>();
        this.executorFactories.put(CreatePatientOperation.class, createPatientProvider);
        this.executorFactories.put(ReadPatientOperation.class, readPatientProvider);
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