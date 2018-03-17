/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.empi.data;

import javax.annotation.concurrent.Immutable;
import java.util.Objects;

/**
 * Instances of this class represent the internal identifier used to identify
 * individual Person entries in the EMPI system.
 *
 * @author Blair Butterworth
 */
@Immutable
public final class InternalIdentifier
{
    private String value;

    public InternalIdentifier(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof InternalIdentifier){
            InternalIdentifier another = (InternalIdentifier)object;
            return Objects.equals(this.value, another.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
