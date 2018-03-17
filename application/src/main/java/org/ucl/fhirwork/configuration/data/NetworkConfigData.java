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
 * Instances of this class contain the information needed to an external server,
 * usually either an EMPI or EHR REST service.
 *
 * @author Chenghui Fan
 * @author Blair Butterworth
 */
@SuppressWarnings("UnusedDeclaration")
public class NetworkConfigData
{
    private String address;
    private String username;
    private String password;

    public NetworkConfigData(String address, String username, String password) {
        this.address = address;
        this.username = username;
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
