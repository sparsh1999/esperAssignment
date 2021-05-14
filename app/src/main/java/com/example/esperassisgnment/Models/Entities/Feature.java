package com.example.esperassisgnment.Models.Entities;

import androidx.room.Entity;

import com.example.esperassisgnment.Helpers.Constants;

@Entity(tableName = Constants.FEATURE_TABLE)
public class Feature {
    private int id ;
    private String name;

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
