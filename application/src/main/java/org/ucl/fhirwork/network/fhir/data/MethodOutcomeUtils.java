/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.fhir.data;

import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.rest.api.MethodOutcome;

public class MethodOutcomeUtils
{
    public static MethodOutcome patientResult(Patient patient)
    {
        MethodOutcome result = new MethodOutcome();
        result.setId(new IdDt("Patient", patient.getId().getIdPart(), "1"));
        return result;
    }
}
