package com.example.esperassisgnment.Models.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.esperassisgnment.Helpers.Constants;

/**
 * Meta data about Feature
 */
@Entity(tableName = Constants.FEATURE_TABLE)
public class Feature {
    @PrimaryKey
    private int id ;
    private String name;

    public Feature(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
