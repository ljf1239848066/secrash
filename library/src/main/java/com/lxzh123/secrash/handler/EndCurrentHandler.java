package com.lxzh123.secrash.handler;

import android.app.Activity;

import com.lxzh123.secrash.IExceptionHandler;
import com.lxzh123.secrash.WindowManagerGlobal;


public class EndCurrentHandler implements IExceptionHandler {
    @Override
    public boolean handleException(Thread t, Throwable e) {
        e.printStackTrace();
        Activity currentActivity = WindowManagerGlobal.getInstance().getCurrenActivity();
        if (currentActivity != null) {
            currentActivity.finish();
        }
        return false;
    }
}
