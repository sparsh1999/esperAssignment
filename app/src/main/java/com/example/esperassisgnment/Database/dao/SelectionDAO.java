package com.example.esperassisgnment.Database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.esperassisgnment.Helpers.Constants;
import com.example.esperassisgnment.Models.Entities.Selection;

@Dao
public interface SelectionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Selection selection);

    @Query(value = "SELECT * FROM "+ Constants.SELECTION_TABLE+
            " WHERE featureId=:featureId AND optionId=:optionId")
    Selection getSelection(int featureId, int optionId);

    @Query(value = "DELETE FROM "+ Constants.SELECTION_TABLE)
    void deleteAll();
}
