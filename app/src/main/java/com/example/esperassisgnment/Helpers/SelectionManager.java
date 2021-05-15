package com.example.esperassisgnment.Helpers;

import com.example.esperassisgnment.Database.ExclusionRepository;
import com.example.esperassisgnment.Models.Entities.Exclusions;
import com.example.esperassisgnment.Models.Selection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * class to Maintain Selections and exclusions due to the selections
 * selections , set of all selected featureId, optionId
 * exclusionMap , map where key is selection and value is the selections which needs to be excluded due
 * to the key selection.
 */
public class SelectionManager {

    Set<Selection> selections = new HashSet<>();
    ConcurrentHashMap<Selection, Set<Selection>> exclusionMap = new ConcurrentHashMap<>();

    ExclusionRepository exclusionRepo;

    public SelectionManager(){
        exclusionRepo = new ExclusionRepository();
    }

    /**
     * add a selection to selectionSet
     * if another option is already selected for the same feature , then resets it and also clears
     * also adds exclusions due to current selection
     * @param featureId
     * @param optionId
     */
    public void addSelection(int featureId, int optionId){
        Selection selectionObj = new Selection(featureId, optionId);

        // check if this feature is already selected with some other option, if yes reset that first
        // TODO there should be some way to notify adapter about the unselected selection, currently adapter will handle it by own
        Selection alreadySelected = checkIfFeatureAlreadySelectedWithDifferentOption(featureId);
        if (alreadySelected!=null){
            removeSelection(alreadySelected.getFeatureId(), alreadySelected.getOptionId());
        }

        // now selection and exclusions can be added safely
        addExclusions(selectionObj);
        selections.add(selectionObj);
    }

    /**
     * request repository for the exclusion set due to selectionObj and updates in exclusionMap
     * @param selectionObj
     */
    private void addExclusions(Selection selectionObj){

        // get all exclusions due to this selection
        List<Selection> excludedSelections = exclusionRepo.
                getAllExclusionsForASelection(selectionObj.getFeatureId(), selectionObj.getOptionId());

        // initialize exclusion set
        if (exclusionMap.get(selectionObj)==null){
            exclusionMap.put(selectionObj, new HashSet<Selection>());
        }

        // add all exclusions to the exclusionMap for this selection
        if (excludedSelections!=null){
            exclusionMap.get(selectionObj).addAll(excludedSelections);
        }
    }


    /**
     * removes the selection from selectionSet and also removes exclusions due to this selection
     * @param featureId
     * @param optionId
     */
    public void removeSelection(int featureId, int optionId){

        // first remove from the selection list
        selections.remove(new Selection(featureId, optionId));

        // now remove entries from exclusion due to this featureId and optionId
        removeExclusions();

    }

    /**
     * removes all the exclusions for a selection which is not present in selection set but present
     * as a key in exclusionMap
     */
    private void removeExclusions(){
        // if selections is empty the clear exclusionMap as well
        if (selections.size()==0){
            exclusionMap.clear();
            return;
        }

        // now clear the exclusions for the selections which are not selected but are present in ExclusionMap
        for (Map.Entry<Selection, Set<Selection>> entry: exclusionMap.entrySet()){
            if (!selections.contains(entry.getKey())){
                exclusionMap.remove(entry.getKey());
            }
        }
    }


    private Selection checkIfFeatureAlreadySelectedWithDifferentOption(int featureId){
        for (Selection selection : selections){
            if (selection.featureId==featureId){
                return selection;
            }
        }
        return null;
    }
}
