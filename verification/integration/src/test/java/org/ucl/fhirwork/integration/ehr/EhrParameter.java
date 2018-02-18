package org.ucl.fhirwork.integration.ehr;

import org.ucl.fhirwork.integration.common.lang.StringCovertable;

public enum EhrParameter implements StringCovertable
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
