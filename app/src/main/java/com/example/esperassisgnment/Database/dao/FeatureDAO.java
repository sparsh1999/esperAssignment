package com.example.esperassisgnment.Database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.esperassisgnment.Helpers.Constants;
import com.example.esperassisgnment.Models.Entities.Feature;

import java.util.List;


@Dao
public interface FeatureDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Feature feature);

    @Update
    void update(Feature... features);

    @Delete
    void delete(Feature... features);


    @Query(value = "DELETE FROM "+ Constants.FEATURE_TABLE)
    void deleteAll();

    @Query(value = "SELECT * FROM "+Constants.FEATURE_TABLE)
    List<Feature> getAllFeatures();
}