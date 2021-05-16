package com.example.esperassisgnment.Database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.esperassisgnment.Helpers.Constants;
import com.example.esperassisgnment.Models.Entities.Exclusions;
import com.example.esperassisgnment.Models.Entities.Selection;

import java.util.List;

@Dao
public interface ExclusionDAO {
    @Insert
    void insert(Exclusions... exclusions);

    @Query(value =
            "SELECT SEC.* FROM ( "+
            "SELECT selection1Id selectionId FROM "+ Constants.EXCLUSION_TABLE+" WHERE selection2Id=:selectionId " +
            "UNION " +
            "SELECT selection2Id selectionId FROM "+ Constants.EXCLUSION_TABLE+" WHERE selection1Id=:selectionId "+
            " )A JOIN "+Constants.SELECTION_TABLE +" SEC ON SEC.id = A.selectionId"
          )
    List<Selection> getNotAllowedSelections(int selectionId);


}
