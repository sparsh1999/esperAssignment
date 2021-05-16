package com.example.esperassisgnment.Models.Entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.esperassisgnment.Helpers.Constants;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = Constants.SELECTION_TABLE)
public class Selection implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName(value = "feature_id")
    public int featureId;
    @SerializedName(value = "options_id")
    public int optionId;

    public int getId() {
        return id;
    }

    public int getFeatureId() {
        return featureId;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFeatureId(int featureId) {
        this.featureId = featureId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public Selection(int featureId, int optionId){
        this.featureId = featureId;
        this.optionId = optionId;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(this.featureId+"0"+this.optionId);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Selection){
            Selection selection = (Selection)obj;
            if (selection.optionId==this.optionId && selection.featureId==this.featureId)
                return true;
        }
        return false ;
    }

    @NonNull
    @Override
    public String toString() {
        return "featureId "+getFeatureId()+" optionId "+getOptionId();
    }
}