package com.example.esperassisgnment.Models;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.esperassisgnment.Helpers.Constants;

@Entity(tableName = Constants.SELECTION_TABLE)
public class Selection {

    @PrimaryKey(autoGenerate = true)
    private int id;
    public int featureId;
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
}
