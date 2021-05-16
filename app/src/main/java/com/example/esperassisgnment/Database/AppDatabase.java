package com.example.esperassisgnment.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.esperassisgnment.Database.dao.ExclusionDAO;
import com.example.esperassisgnment.Database.dao.FeatureDAO;
import com.example.esperassisgnment.Database.dao.OptionsDAO;
import com.example.esperassisgnment.Database.dao.SelectionDAO;
import com.example.esperassisgnment.Models.Entities.Exclusions;
import com.example.esperassisgnment.Models.Entities.Feature;
import com.example.esperassisgnment.Models.Entities.Options;
import com.example.esperassisgnment.Models.Entities.Selection;

@Database(entities = {Feature.class, Options.class, Exclusions.class, Selection.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final String TAG = "AppDatabase";

    public abstract FeatureDAO getFeatureDAO();
    public abstract OptionsDAO getOptionsDAO();
    public abstract ExclusionDAO getExclusionsDAO();
    public abstract SelectionDAO getSelectionDAO();

    //DB Connections
    public static AppDatabase databaseConnection(Context context){
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "db-esper")
                        .allowMainThreadQueries()
                        .build();
    }
}