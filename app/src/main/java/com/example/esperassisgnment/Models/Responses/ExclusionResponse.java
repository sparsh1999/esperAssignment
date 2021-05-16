package com.example.esperassisgnment.Models.Responses;

import com.example.esperassisgnment.Models.Entities.Selection;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExclusionResponse implements Serializable {

    public List<Selection> exclusion;

    public ExclusionResponse(List<Selection> exclusion){
        this.exclusion = exclusion;
    }
}
