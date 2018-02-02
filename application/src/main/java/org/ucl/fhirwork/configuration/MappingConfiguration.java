/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.configuration;

import java.util.List;

public class MappingConfiguration
{
    public List<MappingSpecification> mappings;

    public MappingConfiguration(List<MappingSpecification> mappings) {
        this.mappings = mappings;
    }

    public List<MappingSpecification> getMappings() {
        return mappings;
    }
}
