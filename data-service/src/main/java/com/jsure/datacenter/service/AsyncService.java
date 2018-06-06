package com.jsure.datacenter.service;

import java.util.concurrent.Future;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/6/5
 * @Time: 10:20
 * I am a Code Man -_-!
 */
public interface AsyncService {

    /**
     * 执行异步任务
     */
    void executeAsync();

    /**
     * 带参数的异步调用 异步方法可以传入参数
     */
    void executeAsync(String s);

    /**
     *  异常调用返回Future(有返回值，当调用get()方法时会阻塞主线程)
     * @param i
     * @return
     */
    Future<String> executeAsync(Integer i);
}
