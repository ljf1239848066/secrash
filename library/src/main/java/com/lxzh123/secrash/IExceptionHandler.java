package com.lxzh123.secrash;

public interface IExceptionHandler {
    /**
     * @param t 发送异常的线程
     * @param e 发生的异常
     * @return true 退出  false 继续工作
     */
    boolean handleException(Thread t, Throwable e);
}
