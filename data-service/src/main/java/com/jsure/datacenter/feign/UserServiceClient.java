package com.jsure.datacenter.feign;

import com.jsure.datacenter.model.model.TUser;
import com.jsure.datacenter.model.param.UserParam;
import com.jsure.datacenter.model.result.TUserResult;
import com.jsure.datacenter.utils.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Author: wuxiaobiao
 * @Description: springcloud feign
 * @Date: Created in 2018/6/7
 * @Time: 15:45
 * I am a Code Man -_-!
 */
@FeignClient(name = "data-core",path = "api/server")
public interface UserServiceClient {

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    Response<List<TUserResult>> findUserList(UserParam userParam);

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    Response<Integer> insertSelective(TUser user);
}
