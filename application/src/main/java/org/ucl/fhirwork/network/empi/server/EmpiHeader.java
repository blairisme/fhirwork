/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.empi.server;

public enum EmpiHeader
{
    SessionKey ("OPENEMPI_SESSION_KEY");

    private String value;

    EmpiHeader(String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return value;
    }
}