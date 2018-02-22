/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.configuration.data;

/**
 * Instances of this class contain the information needed to generate an EHR
 * AQL query from a given LOINC code.
 *
 * @author Chenghui Fan
 * @author Blair Butterworth
 */
@SuppressWarnings("UnusedDeclaration")
public class MappingConfigData
{
    private String text;
    private String archetype;
    private String date;
    private String magnitude;
    private String unit;

    public MappingConfigData(
        String text,
        String archetype,
        String date,
        String magnitude,
        String unit)
    {
        this.text = text;
        this.archetype = archetype;
        this.date = date;
        this.magnitude = magnitude;
        this.unit = unit;
    }

    public String getArchetype() {
        return archetype;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getUnit() {
        return unit;
    }
}
