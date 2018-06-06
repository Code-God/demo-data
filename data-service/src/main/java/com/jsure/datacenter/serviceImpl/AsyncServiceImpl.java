package com.jsure.datacenter.serviceImpl;

import com.jsure.datacenter.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @Author: wuxiaobiao
 * @Description: 利用线程池异步执行
 * @Date: Created in 2018/6/5
 * @Time: 10:21
 * I am a Code Man -_-!
 */
@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {

    @Async("asyncServiceExecutor")
    @Override
    public void executeAsync() {
        log.info("start executeAsync");
        try{
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("end executeAsync");
    }

    /**
     * 对于返回值是void，异常会被AsyncUncaughtExceptionHandler处理掉
     * @param s
     */
    @Async("asyncServiceExecutor")
    @Override
    public void executeAsync(String s) {
        log.info("executeAsync, parementer={}", s);
        throw new IllegalArgumentException(s);
    }

    /**
     *  对于返回值是Future，异常不会被AsyncUncaughtExceptionHandler处理，需要我们在方法中捕获异常并处理
     *  或者在调用方在调用Futrue.get时捕获异常进行处理
     * @param i
     * @return
     */
    @Async("asyncServiceExecutor")
    @Override
    public Future<String> executeAsync(Integer i) {
        log.info("executeAsync, parementer={}", i);
        Future<String> future;
        try {
            Thread.sleep(1000 * 1);
            future = new AsyncResult<String>("success:" + i);
            throw new IllegalArgumentException("a");
        } catch (InterruptedException e) {
            future = new AsyncResult<String>("error");
        }catch(IllegalArgumentException e){
            future = new AsyncResult<String>("error-IllegalArgumentException");
        }
        return future;
    }
}
