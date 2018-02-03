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
import org.ucl.fhirwork.mapping.executor.*;
import org.ucl.fhirwork.network.fhir.operations.common.ConditionalOperation;
import org.ucl.fhirwork.network.fhir.operations.patient.CreatePatientOperation;
import org.ucl.fhirwork.network.fhir.operations.patient.DeletePatientOperation;
import org.ucl.fhirwork.network.fhir.operations.patient.ReadPatientOperation;
import org.ucl.fhirwork.network.fhir.operations.patient.UpdatePatientOperation;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Instances of this class provide {@link Executor}s that perform the actions
 * appropriate for a given {@link Operation}.
 *
 * @author Blair Butterworth
 */
public class MappingService
{
    private Map<Predicate<Operation>, Provider<? extends Executor>> executorFactories;

    @Inject
    public MappingService(
            Provider<CreatePatientExecutor> createPatientProvider,
            Provider<DeletePatientExecutor> deletePatientProvider,
            Provider<ReadPatientExecutor> readPatientProvider,
            Provider<UpdatePatientExecutor> updatePatientProvider,
            Provider<CreatePatientConditionalExecutor> createConditionalProvider,
            Provider<DeletePatientConditionalExecutor> deleteConditionalProvider,
            Provider<UpdatePatientConditionalExecutor> updateConditionalProvider)
    {
        this.executorFactories = new LinkedHashMap<>();
        this.executorFactories.put(isConditionalType(CreatePatientOperation.class), createConditionalProvider);
        this.executorFactories.put(isConditionalType(DeletePatientOperation.class), deleteConditionalProvider);
        this.executorFactories.put(isConditionalType(UpdatePatientOperation.class), updateConditionalProvider);
        this.executorFactories.put(isType(CreatePatientOperation.class), createPatientProvider);
        this.executorFactories.put(isType(DeletePatientOperation.class), deletePatientProvider);
        this.executorFactories.put(isType(ReadPatientOperation.class), readPatientProvider);
        this.executorFactories.put(isType(UpdatePatientOperation.class), updatePatientProvider);
    }

    public Executor getExecutor(Operation operation)
    {
        for (Map.Entry<Predicate<Operation>, Provider<? extends Executor>> entry: executorFactories.entrySet())
        {
            Predicate<Operation> executorPredicate = entry.getKey();
            if (executorPredicate.test(operation))
            {
                Provider<? extends Executor> factory = entry.getValue();
                Executor executor = factory.get();
                executor.setOperation(operation);
                return executor;
            }
        }
        throw new UnsupportedOperationException();
    }

    private static Predicate<Operation> isType(Class<?> type)
    {
        return (operation) -> operation.getClass() == type;
    }

    private static Predicate<Operation> isConditionalType(Class<? extends ConditionalOperation> type)
    {
        return (operation) -> {
            if (operation.getClass() == type){
                return ((ConditionalOperation)operation).getSearchParameters() != null;
            }
            return false;
        };
    }
}