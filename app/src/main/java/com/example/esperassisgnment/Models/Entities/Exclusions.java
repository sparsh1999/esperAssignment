package com.example.esperassisgnment.Models.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.esperassisgnment.Helpers.Constants;

/**
 * stores two selections which cannot occur together, each selection is saved in selection Table
 * and a foreign key is maintained int this class.
 */
@Entity(tableName = Constants.EXCLUSION_TABLE)
public class Exclusions {

    @PrimaryKey(autoGenerate = true)
    private int id ;

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


}
