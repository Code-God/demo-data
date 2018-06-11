package com.jsure.datacenter.service.userservice;

import com.jsure.datacenter.model.model.TRole;
import com.jsure.datacenter.model.param.TokenPram;
import com.jsure.datacenter.model.param.UserInfoParam;
import com.jsure.datacenter.model.param.UserParam;
import com.jsure.datacenter.model.result.TUserResult;

import java.util.List;
import java.util.Map;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/5/21
 * @Time: 15:07
 * I am a Code Man -_-!
 */
public interface TokenService {

    Map<String, Object> login(TokenPram tokenPram);

    TUserResult findUserByUserName(String userName);

    TRole findPermissionByRoleId(Integer roleId);

    Map<String, Object> updateUsers(UserInfoParam user);

    Map<String, Object> addUsers(UserInfoParam users);

    Map<String, Object> deleteUsers(Integer[] usersId);

    List<TUserResult> findUserList(UserParam users);

    Map<String, Object> addUsers2(UserInfoParam userParam);
}
