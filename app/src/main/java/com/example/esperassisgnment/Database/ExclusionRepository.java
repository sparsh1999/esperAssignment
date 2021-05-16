package com.example.esperassisgnment.Database;

import android.util.Log;

import com.example.esperassisgnment.App;
import com.example.esperassisgnment.Database.dao.ExclusionDAO;
import com.example.esperassisgnment.Database.dao.SelectionDAO;
import com.example.esperassisgnment.Models.Entities.Selection;
import java.util.List;

public class ExclusionRepository {

    ExclusionDAO exclusionDAO;
    SelectionDAO selectionDAO;

    public ExclusionRepository(){
        exclusionDAO = App.getAppInstance().db.getExclusionsDAO();
        selectionDAO = App.getAppInstance().db.getSelectionDAO();
    }

    public List<Selection> getAllExclusionsForASelection(int featureId, int optionId){
        List<Selection> excludedSelections=null;
        Selection selection = selectionDAO.getSelection(featureId, optionId);

        if (selection!=null){
            excludedSelections = exclusionDAO.getNotAllowedSelections(selection.getId());
        }

        if (excludedSelections!=null)
        Log.d("someting ", excludedSelections.toString());
        else{
            Log.d("null received ", "for selectionId "+featureId+" "+optionId);
        }

        return excludedSelections;
    }
}
