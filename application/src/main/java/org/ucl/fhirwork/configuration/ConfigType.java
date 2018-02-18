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

import com.google.gson.annotations.SerializedName;

/**
 * Options in this enumeration specify identifiers for different configuration
 * files.
 *
 * @author Chenghui Fan
 * @author Blair Butterworth
 */
public enum ConfigType
{
    @SerializedName("network")
	Network,

    @SerializedName("mapping")
    Mapping
}
