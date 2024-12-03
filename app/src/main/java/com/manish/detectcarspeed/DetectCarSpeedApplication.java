package com.manish.detectcarspeed;

import android.app.Application;
import android.content.Context;

public class DetectCarSpeedApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static Context getAppContext() {
        return sContext;
    }
}
