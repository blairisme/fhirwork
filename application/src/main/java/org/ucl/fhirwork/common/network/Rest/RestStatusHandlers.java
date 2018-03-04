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

import java.util.Arrays;
import java.util.Collection;

public class RestStatusHandlers
{
    public static RestStatusHandler throwOnFailedStatus() {
        return (RestResponse response) -> {
            int code = response.getStatusCode();
            return (code >= 200 && code <= 299);
        };
    }

    public static RestStatusHandler throwOnFailureExcept(Integer ... excluding) {
        Collection<Integer> excludes = Arrays.asList(excluding);
        return throwOnFailureExcept(excludes);
    }

    public static RestStatusHandler throwOnFailureExcept(Collection<Integer> excluding) {
        return (RestResponse response) -> {
            int code = response.getStatusCode();
            return (code >= 200 && code <= 299) || excluding.contains(code);
        };
    }
}
