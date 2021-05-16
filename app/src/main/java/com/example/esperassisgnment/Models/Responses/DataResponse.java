package com.example.esperassisgnment.Models.Responses;

import com.example.esperassisgnment.Models.Data;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataResponse implements Serializable {

    @SerializedName(value = "features")
    public List<FeatureResponse> features;

    @SerializedName(value = "exclusions")
    public JsonArray exclusionData;

    public List<ExclusionResponse> exclusionResponses = new ArrayList<>();
}
