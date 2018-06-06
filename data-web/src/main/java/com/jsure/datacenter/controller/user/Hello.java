package com.jsure.datacenter.controller.user;

import com.jsure.datacenter.service.AsyncService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/6/5
 * @Time: 10:28
 * I am a Code Man -_-!
 */
@Slf4j
@RestController
@RequestMapping(value = "/bc")
@Api(tags="测试接口模块",description = "测试")
public class Hello {

    @Autowired
    private AsyncService asyncService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String submit() throws ExecutionException, InterruptedException {
        log.info("start submit");

        //调用service层的任务
        asyncService.executeAsync();
        asyncService.executeAsync("hello");
        Future<String> future = asyncService.executeAsync(666);
        System.out.println(future.get());
        log.info("end submit");
      
        return "success-this is a test master -4";
    }
}
