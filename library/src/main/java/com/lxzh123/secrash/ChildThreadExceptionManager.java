package com.lxzh123.secrash;

import com.lxzh123.secrash.handler.IgnoreHandler;

class ChildThreadExceptionManager {
    IExceptionHandler get(Throwable e) {
        return new IgnoreHandler();
    }
}
