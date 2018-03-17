package org.ucl.fhirwork.integration.ehr.model.composition;

import com.google.gson.annotations.SerializedName;
import org.ucl.fhirwork.integration.cucumber.HealthData;

public class GrowthChartComposition extends FlatComposition
{
    @SerializedName("smart_growth_report/body_weight/weight|magnitude")
    private int weightValue;

    @SerializedName("smart_growth_report/body_weight/weight|unit")
    private String weightUnit;

    @SerializedName("smart_growth_report/height_length/any_event:0/height_length|magnitude")
    private int heightValue;

    @SerializedName("smart_growth_report/height_length/any_event:0/height_length|unit")
    private String heightUnit;

    @SerializedName("smart_growth_report/body_mass_index/any_event:0/body_mass_index|magnitude")
    private int bmiValue;

    @SerializedName("smart_growth_report/body_mass_index/any_event:0/body_mass_index|unit")
    private String bmiUnit;

    @SerializedName("smart_growth_report/head_circumference/any_event:0/head_circumference|magnitude")
    private int headValue;

    @SerializedName("smart_growth_report/head_circumference/any_event:0/head_circumference|unit")
    private String headUnit;

    @SerializedName("smart_growth_report/skeletal_age/any_event:0/skeletal_age")
    private String skeletalAge;

    public GrowthChartComposition(String time, int weight, int height, int bmi, int headCircumference)
    {
        super(time);
        this.weightUnit = "kg";
        this.weightValue = weight;
        this.heightUnit = "cm";
        this.heightValue = height;
        this.bmiUnit = "kg/m2";
        this.bmiValue = bmi;
        this.headUnit = "cm";
        this.headValue = headCircumference;
        this.skeletalAge = "P6Y";
    }

    public static GrowthChartComposition fromHealthData(HealthData data)
    {
        return new GrowthChartComposition(
                data.getDate(),
                data.getWeight(),
                data.getHeight(),
                data.getBmi(),
                data.getHeadCircumference());
    }

    @Override
    public String getCompositionId() {
        return "Smart Growth Chart Data.v0";
    }
}
