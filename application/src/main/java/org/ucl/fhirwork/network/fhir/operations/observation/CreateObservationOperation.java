/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.fhir.operations.observation;

import ca.uhn.fhir.model.dstu2.resource.Observation;
import org.ucl.fhirwork.network.fhir.operations.common.ConditionalOperation;

/**
 * Instances of this class represent the FHIR create observation operation.
 *
 * @author Alperen Karaoglu
 */
public class CreateObservationOperation extends ConditionalOperation {

    private Observation observation;

    public CreateObservationOperation(Observation observation) {
        this.observation = observation;
    }

    public Observation getObservation() {
        return observation;
    }
}
