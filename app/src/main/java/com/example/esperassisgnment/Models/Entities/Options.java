package com.example.esperassisgnment.Models.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.esperassisgnment.Helpers.Constants;

import java.io.Serializable;

/**
 * Meta Data about Option , contains a foreign key(not actual constraint) to feature table
 */
@Entity(tableName = Constants.OPTIONS_TABLE)
public class Options implements Serializable {
    @PrimaryKey
    private int id ;
    // foreign key to Feature Table
    private int featureId;
    private String name;
    private String icon;

    public Options(){}

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getFeatureId() {
        return featureId;
    }

    public String getIcon() {
        return icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFeatureId(int featureId) {
        this.featureId = featureId;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
