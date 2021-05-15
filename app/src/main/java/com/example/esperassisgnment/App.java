package com.example.esperassisgnment;

import android.app.Application;

import com.example.esperassisgnment.Database.AppDatabase;
import com.squareup.picasso.Picasso;

public class App extends Application {

    private static App app;
    public AppDatabase db;

    public static App getAppInstance(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        db = AppDatabase.databaseConnection(app);
    }


}
