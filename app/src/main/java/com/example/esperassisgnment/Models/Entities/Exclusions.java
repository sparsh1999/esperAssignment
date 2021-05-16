package com.example.esperassisgnment.Models.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.esperassisgnment.Helpers.Constants;

@Entity(tableName = Constants.EXCLUSION_TABLE)
public class Exclusions {

    @PrimaryKey(autoGenerate = true)
    private int id ;
//    private int featureId1;
//    private int optionId1;
//    private int featureId2;
//    private int optionId2;

    @ForeignKey(entity = Selection.class, onDelete = ForeignKey.CASCADE,
            parentColumns = "id", childColumns = "selection1Id")
    private int selection1Id;

    @ForeignKey(entity = Selection.class, onDelete = ForeignKey.CASCADE,
            parentColumns = "id", childColumns = "selection2Id")
    private int selection2Id;

    public Exclusions(){}

    @Ignore
    public Exclusions(int selection1Id, int selection2Id){
        this.selection1Id = selection1Id;
        this.selection2Id = selection2Id;
    }
    public int getId() {
        return id;
    }

    public int getSelection1Id() {
        return selection1Id;
    }

    public int getSelection2Id() {
        return selection2Id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSelection1Id(int selection1Id) {
        this.selection1Id = selection1Id;
    }

    public void setSelection2Id(int selection2Id) {
        this.selection2Id = selection2Id;
    }

    //    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getFeatureId1() {
//        return featureId1;
//    }
//
//    public void setFeatureId1(int featureId1) {
//        this.featureId1 = featureId1;
//    }
//
//    public int getOptionId1() {
//        return optionId1;
//    }
//
//    public void setOptionId1(int optionId1) {
//        this.optionId1 = optionId1;
//    }
//
//    public int getFeatureId2() {
//        return featureId2;
//    }
//
//    public void setFeatureId2(int featureId2) {
//        this.featureId2 = featureId2;
//    }
//
//    public int getOptionId2() {
//        return optionId2;
//    }
//
//    public void setOptionId2(int optionId2) {
//        this.optionId2 = optionId2;
//    }

}
