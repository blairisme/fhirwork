/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.ehr.data;

import com.google.gson.annotations.SerializedName;

public class CompositionResult
{
    @SerializedName("#0")
    private Composition composition;

    public CompositionResult(Composition composition) {
        this.composition = composition;
    }

    public Composition getComposition() {
        return composition;
    }
}
