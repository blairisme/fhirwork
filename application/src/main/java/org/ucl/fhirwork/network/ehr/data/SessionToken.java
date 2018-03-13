/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.ehr.data;

/**
 * Instances of this class are returned after successfully authenticating to an
 * OpenEHR server, and contain a token used when making subsequent OpenEHR
 * service calls to prove authentication.
 *
 * @author Blair Butterworth
 */
public class SessionToken
{
    private String sessionId;

    public SessionToken(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
