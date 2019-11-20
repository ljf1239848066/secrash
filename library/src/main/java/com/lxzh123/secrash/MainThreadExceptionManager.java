package com.lxzh123.secrash;

import com.lxzh123.secrash.handler.EndCurrentHandler;
import com.lxzh123.secrash.handler.IgnoreHandler;
import com.lxzh123.secrash.handler.KillAppHandler;

class MainThreadExceptionManager {
    IExceptionHandler get(Throwable e) {
        if(e instanceof IllegalStateException){
            return new EndCurrentHandler();
        }

        if(e instanceof SecurityException){
            return new KillAppHandler();
        }

        return new IgnoreHandler();
    }
}
