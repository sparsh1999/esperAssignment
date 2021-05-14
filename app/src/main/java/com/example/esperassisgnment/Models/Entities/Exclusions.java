package com.example.esperassisgnment.Models.Entities;

import android.media.session.MediaController;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.esperassisgnment.Helpers.Constants;

@Entity(tableName = Constants.EXCLUSION_TABLE)
public class Exclusions {

    @PrimaryKey(autoGenerate = true)
    private int id ;
    private int featureId1;
    private int optionId1;
    private int featureId2;
    private int optionId2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFeatureId1() {
        return featureId1;
    }

    public void setFeatureId1(int featureId1) {
        this.featureId1 = featureId1;
    }

    public int getOptionId1() {
        return optionId1;
    }

    public void setOptionId1(int optionId1) {
        this.optionId1 = optionId1;
    }

    public int getFeatureId2() {
        return featureId2;
    }

    public void setFeatureId2(int featureId2) {
        this.featureId2 = featureId2;
    }

    public int getOptionId2() {
        return optionId2;
    }

    public void setOptionId2(int optionId2) {
        this.optionId2 = optionId2;
    }
}
