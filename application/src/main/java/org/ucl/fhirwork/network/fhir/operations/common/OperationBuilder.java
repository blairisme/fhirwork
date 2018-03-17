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
import org.ucl.fhirwork.common.framework.Operation;
import java.util.Map;

/**
 * Instances of this class represent the base class for those classes that
 * create {@link Operation} instances, usually given either an identifier or a
 * collection of search parameters.
 *
 * @param <T>   the type of {@link Operation} being constructed.
 *
 * @author Blair Butterworth
 */
public abstract class OperationBuilder <T extends Operation>
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