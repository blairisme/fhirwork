package org.ucl.fhirwork.integration.ehr.model.composition;

import com.google.gson.annotations.SerializedName;
import org.ucl.fhirwork.integration.cucumber.HealthData;

public class HeightWeightComposition extends FlatComposition
{
    @SerializedName("height_weight/height_length/any_event:0/height|magnitude")
    private int heightValue;

    @SerializedName("height_weight/height_length/any_event:0/height|unit")
    private String heightUnit;

    @SerializedName("height_weight/body_weight/weight|magnitude")
    private int weightValue;

    @SerializedName("height_weight/body_weight/weight|unit")
    private String weightUnit;

    public HeightWeightComposition(String time, int height, int weight)
    {
        super(time);
        this.heightValue = height;
        this.heightUnit = "cm";
        this.weightValue = weight;
        this.weightUnit = "kg";
    }

    @Override
    public String getCompositionId() {
        return "RIPPLE - Height_Weight.v1";
    }

    public static HeightWeightComposition fromHealthData(HealthData data) {
        return new HeightWeightComposition(data.getDate(), data.getHeight(), data.getWeight());
    }
}