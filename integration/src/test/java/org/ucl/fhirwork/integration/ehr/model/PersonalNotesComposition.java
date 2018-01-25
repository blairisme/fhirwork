package org.ucl.fhirwork.integration.ehr.model;

import com.google.gson.annotations.SerializedName;

public class PersonalNotesComposition extends FlatComposition
{
    @SerializedName("personal_notes/clinical_synopsis:0/_name|value") private String title;
    @SerializedName("personal_notes/clinical_synopsis:0/notes") private String description;

    public PersonalNotesComposition(String title, String description)
    {
        this.title = title;
        this.description = description;
    }

    @Override
    public String getCompositionId() {
        return "RIPPLE - Personal Notes.v1";
    }
}
