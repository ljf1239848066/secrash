package com.lxzh123.secrashapp;

import android.app.Application;

import com.lxzh123.secrash.SeCrash;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SeCrash.install();
    }
}
