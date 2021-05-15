package com.example.esperassisgnment.Helpers;

import com.example.esperassisgnment.Models.Entities.Exclusions;
import com.example.esperassisgnment.Models.Selection;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class SelectionManager {
    Set<Selection> selections = new HashSet<>();

    // TODO convert exclusion into some kind of map for easier removal and additions
    Set<Exclusions> exclusions = new HashSet<>();

    public void addSelection(int featureId, int optionId){
        if (checkIfFeatureAlreadySelectedWithDifferentOption(featureId)){
            removeSelection();
        }
    }

    public void removeSelection(int featureId, int optionId){

        // first remove from the selection list
        selections.remove(new Selection(featureId, optionId));

        // now remove entries from exclusion due to this featureId and optionId
        removeExclusions();

    }

    private void removeExclusions(){
        if (selections.size()==0){
            exclusions.clear();
        }
        // TODO implement the rest of funcationlaity properly
//        for (Selection selection: selections){
//            ListIterator<>
//            for (Exclusions exclusion: exclusions){
//                if ((selection.featureId==exclusion.getFeatureId1() && selection.optionId==exclusion.getOptionId1())
//                        || (selection.featureId==exclusion.getFeatureId2() && selection.optionId==exclusion.getOptionId2()) ){
//
//                }
//            }
//        }
    }

    private void addExclusions(Selection selection){
        // get all Exclusions

    }


    private boolean checkIfFeatureAlreadySelectedWithDifferentOption(int featureId){
        for (Selection selection : selections){
            if (selection.featureId==featureId){
                return true;
            }
        }
        return false;
    }
}
