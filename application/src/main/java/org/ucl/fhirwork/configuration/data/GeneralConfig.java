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

public class GeneralConfig
{
    private String ehrIdSystem;

    public GeneralConfig(String ehrIdSystem) {
        this.ehrIdSystem = ehrIdSystem;
    }

    public String getEhrIdSystem() {
        return ehrIdSystem;
    }

    public void setEhrIdSystem(String ehrIdSystem) {
        this.ehrIdSystem = ehrIdSystem;
    }
}
