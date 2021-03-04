package com.lxzh123.secrashapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "Test_Crash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                crash();
                doSomething();
            }
        });

        findViewById(R.id.another).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crashInAnotherThread();
            }
        });

        Log.d(TAG, "Main Thread:" + Thread.currentThread().getName() + " t:" + Thread.currentThread());

        overrideMainHandler();

        final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                e.printStackTrace();
                //收集Crash信息
                //collectException(e);
                //涉及网络操作不推荐在这里进行上报
                //uploadException(e);
                defaultHandler.uncaughtException(t, e);
            }
        });

    }

    Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
            Log.d(TAG, "uncaughtException t=" + t.getName() + " t:" + t);
            Log.d(TAG, "uncaughtException e=" + e);
            e.printStackTrace();
        }
    };

    private void overrideMainHandler() {
//        Thread.currentThread().setUncaughtExceptionHandler(handler);
        Thread.currentThread().setUncaughtExceptionHandler(null);
        Thread.setDefaultUncaughtExceptionHandler(null);
        Method setUncaughtExceptionPreHandlerMethod = null;
        try {
            Class<?> tClass = Thread.class;
            Class<?> uClass = Thread.UncaughtExceptionHandler.class;
            String name1 = "setUncaughtException";
            String name2 = "Test";
            String name3 = "PreHandler";
            String methodName;
            if (name1.startsWith("set")) {
                methodName = name1 + name3;
            } else {
                methodName = name1 + name2;
            }
            setUncaughtExceptionPreHandlerMethod = tClass.getDeclaredMethod(methodName, uClass);
//            setUncaughtExceptionPreHandlerMethod.invoke(tClass, handler);
            setUncaughtExceptionPreHandlerMethod.invoke(tClass, new Object[]{null});
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                e.printStackTrace();
            }
        });

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                e.printStackTrace();
                //collectException(e);
                //uploadException(e);
            }
        });
    }

    private void crash() {
        int i = 10 / 0;
        Log.i("aaa", "bbb i=" + i);
    }

    private void crashInAnotherThread() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Thread.currentThread().setUncaughtExceptionHandler(handler);
                crash();
            }
        }.start();
    }

    @TargetApi(26)
    private void doSomething() {
        Trace.beginSection("doSomething");
        double total = 0;
        for (int i = 0; i < 100000000; i++) {
            double a1 = 134.535 / i;
            total += a1;
        }
        Log.d(TAG, "doSomething total=" + total);
        Trace.endSection();
    }
}
