/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.common.network.http;

/**
 * Options in this enumeration specify MIME types, identifiers of file formats.
 *
 * @author Blair Butterworth
 */
public enum MimeType
{
    Json    ("application/json"),
    Xml     ("application/xml");

    private String value;

    MimeType(String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return value;
    }
}
