package com.lxzh123.secrash.handler;

import com.lxzh123.secrash.IExceptionHandler;

public class KillAppHandler implements IExceptionHandler {
    @Override
    public boolean handleException(Thread t, Throwable e) {
        e.printStackTrace();
        android.os.Process.killProcess(android.os.Process.myPid());
        return true;
    }
}