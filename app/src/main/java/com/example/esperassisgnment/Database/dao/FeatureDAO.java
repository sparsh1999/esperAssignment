package com.example.esperassisgnment.Database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import com.example.esperassisgnment.Models.Entities.Feature;


@Dao
public interface FeatureDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Feature feature);

    @Update
    void update(Feature... features);

    @Delete
    void delete(Feature... features);

}