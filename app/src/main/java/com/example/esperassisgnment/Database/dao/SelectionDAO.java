package com.example.esperassisgnment.Database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.esperassisgnment.Helpers.Constants;
import com.example.esperassisgnment.Models.Selection;

@Dao
public interface SelectionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Selection selection);

    @Query(value = "SELECT * FROM "+ Constants.SELECTION_TABLE+
            " WHERE featureId=:featureId AND optionId=:optionId")
    Selection getSelection(int featureId, int optionId);

}
