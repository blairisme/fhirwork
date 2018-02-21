/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */
package org.ucl.fhirwork.network.fhir.operations.common;

import org.apache.commons.lang3.Validate;
import org.ucl.fhirwork.common.framework.Operation;
import org.ucl.fhirwork.network.fhir.data.SearchParameter;

import java.util.Collections;
import java.util.Map;

/**
 * Instances of this claess represent an action requested by the user, but one
 * whose target is dependent on conditional criteria. E.g., delete patients
 * named bob.
 *
 * @author Blair Butterworth
 */
public abstract class ConditionalOperation implements Operation
{
    private Map<SearchParameter, Object> searchParameters;

    protected ConditionalOperation() {
        searchParameters = null; //Collections.emptyMap();
    }

    protected ConditionalOperation(Map<SearchParameter, Object> searchParameters) {
        Validate.notNull(searchParameters);
        this.searchParameters = Collections.unmodifiableMap(searchParameters);
    }

    public Map<SearchParameter, Object> getSearchParameters() {
        return searchParameters;
    }
}
