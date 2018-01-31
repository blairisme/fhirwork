package org.ucl.fhirwork.network.ehr.server;

public enum EhrParameter {

    Aql                 ("aql"),
    EhrId               ("ehrId"),
    Format              ("format"),
    SubjectId           ("subjectId"),
    SubjectNamespace    ("subjectNamespace"),
    TemplateId          ("templateId"),
    Password            ("password"),
    Username            ("username"),
    SessionId           ("Ehr-Session");
    private String value;

    EhrParameter(String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return value;
    }
}
