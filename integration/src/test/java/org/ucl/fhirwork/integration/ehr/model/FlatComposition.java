package org.ucl.fhirwork.integration.ehr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class FlatComposition
{
    @SerializedName("ctx/language") private String language;
    @SerializedName("ctx/territory") private String territory;
    @SerializedName("ctx/composer_name") private String composer;
    @SerializedName("ctx/time") private String time;
    @SerializedName("ctx/id_namespace") private String idNamespace;
    @SerializedName("ctx/id_scheme") private String idScheme;
    @SerializedName("ctx/health_care_facility|name") private String facilityName;
    @SerializedName("ctx/health_care_facility|id") private String facilityId;

    public FlatComposition()
    {
        this.language = "en";
        this.territory = "GB";
        this.composer = "Dummy";
        this.time = time;
        this.idNamespace = "Hospital";
        this.idScheme = "HOSPITAL-NS";
        this.facilityName = "Marandia DGH";
        this.facilityId = "9095";
    }

    public abstract String getCompositionId();
}
