
/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To views a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.network.ehr.server;

public enum EhrParameter
{
    Aql                 ("aql"),
    EhrId               ("ehrId"),
    Format              ("format"),
    SubjectId           ("subjectId"),
    SubjectNamespace    ("subjectNamespace"),
    TemplateId          ("templateId"),
    Password            ("password"),
    Username            ("username");

    private String value;

    private EhrParameter(String value)

    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return value;
    }
}
