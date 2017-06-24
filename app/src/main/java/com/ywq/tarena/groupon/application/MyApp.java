package com.ywq.tarena.groupon.application;

import android.app.Application;

/**
 * Created by tarena on 2017/6/19.
 */

public class MyApp extends Application {

    public static MyApp CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this;
    }
}

