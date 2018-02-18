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

import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.TokenOrListParam;
import org.ucl.fhirwork.common.framework.Operation;

/**
 * Instances of this class represent the FHIR create observation operation.
 *
 * @author Blair Butterworth
 */
public class ReadObservationOperation implements Operation
{
    private TokenOrListParam codes;
    private ReferenceParam patient;

    public ReadObservationOperation(TokenOrListParam codes, ReferenceParam patient) {
        this.codes = codes;
        this.patient = patient;
    }

    public TokenOrListParam getCodes() {
        return codes;
    }

    public ReferenceParam getPatient() {
        return patient;
    }
}
