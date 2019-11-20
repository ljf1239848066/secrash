package com.lxzh123.secrash;

import android.os.Handler;
import android.os.Looper;

public class SeCrash {

    private static SeCrash instance = new SeCrash();

    private ExceptionHandlerManager manager;

    private SeCrash() {
        manager = ExceptionHandlerManager.getInstance();
    }

    public static void install() {
        instance.installImp();
    }

    private void installImp() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Looper.loop();
                    } catch (Throwable e) {
                        if(manager.handleMainThreadException(Thread.currentThread(), e)) {
                            return;
                        }
                    }
                }
            }
        });

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                manager.handleChildThreadException(t, e);
            }
        });
    }
}
