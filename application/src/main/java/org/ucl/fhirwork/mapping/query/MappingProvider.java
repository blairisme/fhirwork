/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping.query;

import ca.uhn.fhir.model.dstu2.resource.Observation;
import org.ucl.fhirwork.network.ehr.data.ObservationBundle;
import org.ucl.fhirwork.network.ehr.data.ObservationResult;

import java.util.List;

/**
 * Implementors of this interface convert data between observations and OpenEHR
 * AQL queries.
 *
 * @author Blair Butterworth
 */
public interface MappingProvider
{
    String getQuery(String ehrId);

    List<Observation> getObservations(String code, String patient, ObservationBundle result);
}
