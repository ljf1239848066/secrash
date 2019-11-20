package com.lxzh123.secrash;

class ExceptionHandlerManager {
    private volatile static ExceptionHandlerManager instance;

    private MainThreadExceptionManager mainThreadExceptionManager;
    private ChildThreadExceptionManager childThreadExceptionManager;
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    private ExceptionHandlerManager() {
    }

    public static ExceptionHandlerManager getInstance() {
        if (instance == null) {
            synchronized (ExceptionHandlerManager.class) {
                if (instance == null) {
                    instance = new ExceptionHandlerManager();
                    instance.init();
                }
            }
        }
        return instance;
    }

    private void init() {
        mainThreadExceptionManager = new MainThreadExceptionManager();
        childThreadExceptionManager = new ChildThreadExceptionManager();
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public IExceptionHandler getMainThreadExceptionHandler(Throwable t) {
        return mainThreadExceptionManager.get(t);
    }

    public IExceptionHandler getChildThreadExceptionHandler(Throwable t) {
        return childThreadExceptionManager.get(t);
    }

    public boolean handleMainThreadException(Thread t, Throwable e) {
        IExceptionHandler handler = mainThreadExceptionManager.get(e);
        if (handler == null) {
            defaultUncaughtExceptionHandler.uncaughtException(Thread.currentThread(), e);
            return false;
        } else {
            return handler.handleException(t, e);
        }
    }

    public void handleChildThreadException(Thread t, Throwable e) {
        IExceptionHandler handler = childThreadExceptionManager.get(e);
        if (handler == null) {
            defaultUncaughtExceptionHandler.uncaughtException(t, e);
        } else {
            handler.handleException(t, e);
        }
    }
}
