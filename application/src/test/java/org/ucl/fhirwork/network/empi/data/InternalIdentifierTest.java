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

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class InternalIdentifierTest
{
    @Test
    public void equalsAndHashCodeTest() {
        EqualsVerifier verifier = EqualsVerifier.forClass(InternalIdentifier.class);
        verifier.verify();
    }
}
