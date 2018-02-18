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

/**
 * Implementors of this interface provide methods that are called when
 * configuration data is updated.
 *
 * @author Blair Butterworth
 */
public interface ConfigObserver
{
    void configurationUpdated();
}
