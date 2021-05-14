package com.example.esperassisgnment;

import android.app.Application;

public class App extends Application {

    private static App app;

    public static App getAppInstance(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
