package com.example.esperassisgnment.Database.dao;

import android.graphics.Path;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.esperassisgnment.Helpers.Constants;
import com.example.esperassisgnment.Models.Entities.Options;

import java.util.List;

@Dao
public interface OptionsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Options... Options);

    @Update
    void update(Options... Options);

    @Delete
    void delete(Options... Options);

    @Query(value = "DELETE FROM "+ Constants.OPTIONS_TABLE)
    void deleteAll();

    @Query(value = "SELECT * FROM "+Constants.OPTIONS_TABLE +" WHERE featureId=:featureId")
    List<Options> getAllOptionsForFeature(int featureId);
}
