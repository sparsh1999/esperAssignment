package com.example.esperassisgnment;

import android.app.Application;

import com.example.esperassisgnment.Database.AppDatabase;
import com.example.esperassisgnment.Network.ApiManager;
import com.example.esperassisgnment.Network.ApiService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class App extends Application {

    private static App app;
    public AppDatabase db;
    public ApiService api;
    public Gson gson;

    public static App getAppInstance(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        db = AppDatabase.databaseConnection(app);
        api = ApiManager.getInstance(app).service;
        gson = new Gson();
    }


}
