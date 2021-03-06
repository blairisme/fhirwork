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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.annotation.concurrent.Immutable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Instances of this class are used to obtain a session token, used to access
 * EMPI web services. The authenticate EMPI REST web service accepts
 * AuthenticationRequests using the following format.
 *
 * <pre>{@code
 *
 *  <authenticationRequest>
 *      <password>admin</password>
 *      <username>admin</username>
 *  </authenticationRequest>
 *
 *}</pre>
 *
 * @author Blair Butterworth
 */
@Immutable
@XmlRootElement(name = "authenticationRequest")
@SuppressWarnings("unused")
public final class AuthenticationRequest
{
    private String username;
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;

        if (object instanceof AuthenticationRequest) {
            AuthenticationRequest other = (AuthenticationRequest)object;
            return new EqualsBuilder()
                .append(this.username, other.username)
                .append(this.password, other.password)
                .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(username)
            .append(password)
            .toHashCode();
    }
}