package com.example.esperassisgnment.Database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import com.example.esperassisgnment.Models.Entities.Options;

@Dao
public interface OptionsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Options... Options);

    @Update
    void update(Options... Options);

    @Delete
    void delete(Options... Options);

}
