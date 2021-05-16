package com.example.esperassisgnment.Models.Responses;

import com.example.esperassisgnment.Models.Entities.Options;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FeatureResponse implements Serializable {
    @SerializedName(value = "feature_id")
    public int featureId;

    @SerializedName(value = "name")
    public String name;

    @SerializedName(value = "options")
    public List<Options> options;
}
