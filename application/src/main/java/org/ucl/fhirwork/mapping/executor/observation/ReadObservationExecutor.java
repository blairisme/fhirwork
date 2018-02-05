/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping.executor.observation;

import org.ucl.fhirwork.common.framework.ExecutionException;
import org.ucl.fhirwork.common.framework.Executor;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.network.fhir.operations.observation.ReadObservationOperation;

public class ReadObservationExecutor implements Executor
{
    @Override
    public void setOperation(Operation operation) {
        ReadObservationOperation readObservation = (ReadObservationOperation)operation;
    }

    @Override
    public Object invoke() throws ExecutionException {
        return null;
    }
}
