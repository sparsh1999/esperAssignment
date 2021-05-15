package com.example.esperassisgnment.Database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.esperassisgnment.Helpers.Constants;
import com.example.esperassisgnment.Models.Entities.Exclusions;
import com.example.esperassisgnment.Models.Selection;

import java.util.List;

@Dao
public interface ExclusionDAO {
    @Insert
    void insert(Exclusions... exclusions);

    @Query(value =
            "SELECT selection1Id FROM "+ Constants.EXCLUSION_TABLE+" WHERE selection2Id=:selectionId " +
            "UNION " +
            "SELECT selection2Id FROM "+ Constants.EXCLUSION_TABLE+" WHERE selection1Id=:selectionId "
          )
    List<Selection> getNotAllowedSelections(int selectionId);


}
