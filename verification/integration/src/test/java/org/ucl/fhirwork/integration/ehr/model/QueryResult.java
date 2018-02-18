package org.ucl.fhirwork.integration.ehr.model;

import com.google.gson.annotations.SerializedName;

public class QueryResult
{
    @SerializedName("#0")
    private Composition composition;

    public QueryResult(Composition composition) {
        this.composition = composition;
    }

    public Composition getComposition() {
        return composition;
    }
}
