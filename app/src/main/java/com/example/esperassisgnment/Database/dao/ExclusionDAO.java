package com.example.esperassisgnment.Database.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.esperassisgnment.Models.Entities.Exclusions;

@Dao
public interface ExclusionDAO {
    @Insert
    void insert(Exclusions... exclusions);


}
