package org.ucl.fhirwork.integration.ehr.model.composition;

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

    public FlatComposition(String time)
    {
        this.language = "en";
        this.territory = "GB";
        this.composer = "Dr Tony Shannon";
        this.time = "2016-01-24T00:11:02.518+02:00";// time;
        this.idNamespace = "NHS-UK";
        this.idScheme = "2.16.840.1.113883.2.1.4.3";
        this.facilityName = "Home";
        this.facilityId = "999999-345";
    }

    public abstract String getCompositionId();
}