/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.common.network.Rest;

import java.util.function.Predicate;

/**
 * Implementors of this interface provide a strategy for handling responses
 * from a REST service.
 *
 * @author Blair Butterworth
 */
public interface RestStatusStrategy extends Predicate<RestResponse> {
}
