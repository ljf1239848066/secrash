package com.lxzh123.secrash.handler;


import com.lxzh123.secrash.IExceptionHandler;

public class IgnoreHandler implements IExceptionHandler {
    @Override
    public boolean handleException(Thread t, Throwable e) {
        e.printStackTrace();
        return false;
    }
}
