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

import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.rest.param.DateParam;
import ca.uhn.fhir.rest.param.TokenParam;
import org.ucl.fhirwork.network.fhir.data.SearchParameter;
import org.ucl.fhirwork.network.fhir.data.SearchParameterBuilder;

import java.util.Map;

public abstract class OperationBuilder <T>
{
    private IdDt identifier;
    private SearchParameterBuilder parameterBuilder;

    public OperationBuilder() {
        parameterBuilder = new SearchParameterBuilder();
    }

    public void append(IdDt identifier) {
        this.identifier = identifier;
    }

    public void append(SearchParameter key, TokenParam value) {
        this.parameterBuilder.append(key, value);
    }

    public void append(SearchParameter key, StringDt value) {
        this.parameterBuilder.append(key, value);
    }

    public void append(SearchParameter key, DateParam value) {
        this.parameterBuilder.append(key, value);
    }

    public T build(){
        if (identifier != null){
            return newOperation(identifier);
        }
        return newOperation(parameterBuilder.build());
    }

    protected abstract T newOperation(IdDt identifier);

    protected abstract T newOperation(Map<SearchParameter, Object> searchParameters);
}