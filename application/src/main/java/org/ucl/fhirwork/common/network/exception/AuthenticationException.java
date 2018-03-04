/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.common.network.exception;

public class AuthenticationException extends NetworkException
{
    private String address;
    private String username;

    public AuthenticationException(String address, String username)
    {
        super("Unable to authenticate to server. User: " + username + ". Address: " + address);
        this.address = address;
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }
}
