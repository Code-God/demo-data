package com.jsure.datacenter.controller.user;

import com.google.common.collect.Maps;
import com.jsure.datacenter.annotation.TestAnnotation;
import com.jsure.datacenter.constant.CustomConstant;
import com.jsure.datacenter.controller.base.BaseController;
import com.jsure.datacenter.exception.CustomException;
import com.jsure.datacenter.model.param.CrudUserParam;
import com.jsure.datacenter.model.param.TokenPram;
import com.jsure.datacenter.model.param.UserInfoParam;
import com.jsure.datacenter.model.param.UserParam;
import com.jsure.datacenter.model.result.TUserResult;
import com.jsure.datacenter.service.userservice.TokenService;
import com.jsure.datacenter.utils.Response;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/5/21
 * @Time: 14:49
 * I am a Code Man -_-!
 */
@Slf4j
@RestController
@RequestMapping(value = "/bc")
@Api(tags="用户管理",description = "测试")
public class LoginTokenController extends BaseController {

    @Autowired
    private TokenService tokenService;


    /**
     * 创建用户
     *
     * @param userParam
     * @return
     */
    @ApiOperation(value = "创建用户", notes = "创建用户，返回创建后的用户信息")
    @ApiImplicitParam(name = "userParam", value = "用户详细实体user", required = true, dataType = "UserInfoParam")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<Response> addUsers(@RequestBody UserInfoParam userParam) {
        Map<String, Object> result = Maps.newHashMap();
        Response r = new Response();
        try {
            checkShiroPermission("add");
            result = tokenService.addUsers(userParam);
            successResult(r, CustomConstant.CREATE_USER_SUCCESS, result);
            log.info("success to addUser, RESULT:{}", result);
        } catch (CustomException e) {
            failedResult(r, e.getCode(), e.getMessage(), result);
            log.error("failed to addUser, RESULT:{},cause:{}", result, e);
        } catch (Exception e) {
            failedResult(r, result);
            log.error("failed to addUser, RESULT:{},cause:{}", result, e);
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 登录
     *
     * @param tokenPram
     * @return
     */
    @TestAnnotation(desc = "测试玩玩")
    @ApiOperation(value = "登录", notes = "根据用户名和密码登录，返回token密钥")
    @ApiImplicitParam(name = "tokenPram", value = "用户令牌实体", required = true, dataType = "TokenPram")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Response> login(@RequestBody TokenPram tokenPram) {
        Map<String, Object> result = Maps.newHashMap();
        Response r = new Response();
        try {
            result = tokenService.login(tokenPram);
            successResult(r, CustomConstant.SYS_LOGIN_SUCCESS, result);
            log.info("success to login, RESULT:{}", result);
        } catch (CustomException e) {
            failedResult(r, e.getCode(), e.getMessage(), result);
            log.error("failed to login, RESULT:{},cause:{}", result, e);
        } catch (Exception e) {
            failedResult(r, result);
            log.error("failed to login, RESULT:{},cause:{}", result, e);
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    @ApiOperation(value = "根据用户名查询用户信息", notes = "根据用户名查询用户信息")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public ResponseEntity<Response> findUsersByUserName(@PathVariable String username) {
        Map<String, Object> result = Maps.newHashMap();
        Response r = new Response();
        try {
            checkShiroPermission("find");
            TUserResult user = tokenService.findUserByUserName(username);
            result.put("user", user);
            successResult(r, CustomConstant.USER_INFO_SUCCESS, result);
            log.info("success to findUserByUserName, RESULT:{}", result);
        } catch (CustomException e) {
            failedResult(r, e.getCode(), e.getMessage(), result);
            log.error("failed to findUserByUserName, RESULT:{},cause:{}", result, e);
        } catch (Exception e) {
            failedResult(r, result);
            log.error("failed to findUserByUserName, RESULT:{},cause:{}", result, e);
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 查询用户列表
     *
     * @param userParam
     * @return
     */
    @ApiOperation(value = "查询用户列表", notes = "查询用户列表,含分页排序")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<Response> findUserList(UserParam userParam) {
        Map<String, Object> result = Maps.newHashMap();
        Response r = new Response();
        try {
            checkShiroPermission("find");
            List<TUserResult> users = tokenService.findUserList(userParam);
            result.put("userList", users);
            successResult(r, CustomConstant.USER_INFO_SUCCESS, result);
            log.info("success to findUserList, RESULT:{}", result);
        } catch (CustomException e) {
            failedResult(r, e.getCode(), e.getMessage(), result);
            log.error("failed to findUserList, RESULT:{},cause:{}", result, e);
        } catch (Exception e) {
            failedResult(r, result);
            log.error("failed to findUserList, RESULT:{},cause:{}", result, e);
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 更新用户资料
     *
     * @param userParam
     * @return
     */
    @ApiOperation(value = "更新用户资料", notes = "根据用户资料修改信息，返回修改后的用户信息")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "userParam", value = "用户详细实体user", required = true, dataType = "UserInfoParam"),
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Integer",paramType = "path")
    })
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Response> updateUsers(@RequestBody UserInfoParam userParam, @PathVariable Integer id) {
        Map<String, Object> result = Maps.newHashMap();
        Response r = new Response();
        try {
            checkShiroPermission("update");
            userParam.setId(id);
            result = tokenService.updateUsers(userParam);
            successResult(r, CustomConstant.UPDATE_USER_SUCCESS, result);
            log.info("success to updateUser, RESULT:{}", result);
        } catch (CustomException e) {
            failedResult(r, e.getCode(), e.getMessage(), result);
            log.error("failed to updateUser, RESULT:{},cause:{}", result, e);
        } catch (Exception e) {
            failedResult(r, result);
            log.error("failed to updateUser, RESULT:{},cause:{}", result, e);
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 删除/批量删除用户
     *
     * @param crudUserParam
     * @return
     */
    @ApiOperation(value = "删除/批量删除用户", notes = "传入用户id数组")
    @ApiImplicitParam(name = "crudUserParam", value = "批量操作用户实体", required = true, dataType = "CrudUserParam")
    @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public ResponseEntity<Response> deleteUsers(@RequestBody CrudUserParam crudUserParam) {
        Map<String, Object> result = Maps.newHashMap();
        Response r = new Response();
        try {
            checkShiroPermission("delete");
            tokenService.deleteUsers(crudUserParam.getId());
            successResult(r, CustomConstant.DELETE_USER_SUCCESS, result);
            log.info("success to updateUser, RESULT:{}", result);
        } catch (CustomException e) {
            failedResult(r, e.getCode(), e.getMessage(), result);
            log.error("failed to updateUser, RESULT:{},cause:{}", result, e);
        } catch (Exception e) {
            failedResult(r, result);
            log.error("failed to updateUser, RESULT:{},cause:{}", result, e);
        }
        return ResponseEntity.ok(r);
    }


    /**
     * 测试feigin
     *
     * @param userParam
     * @return
     */
    @ApiOperation(value = "测试feigin", notes = "创建用户，返回创建后的用户信息")
    @ApiImplicitParam(name = "userParam", value = "用户详细实体user", required = true, dataType = "UserInfoParam")
    @RequestMapping(value = "/users2", method = RequestMethod.POST)
    public ResponseEntity<Response> addUsers2(@RequestBody UserInfoParam userParam) {
        Map<String, Object> result = Maps.newHashMap();
        Response r = new Response();
        try {
            checkShiroPermission("add");
            result = tokenService.addUsers2(userParam);
            successResult(r, CustomConstant.CREATE_USER_SUCCESS, result);
            log.info("success to addUser2, RESULT:{}", result);
        } catch (CustomException e) {
            failedResult(r, e.getCode(), e.getMessage(), result);
            log.error("failed to addUser2, RESULT:{},cause:{}", result, e);
        } catch (Exception e) {
            failedResult(r, result);
            log.error("failed to addUser2, RESULT:{},cause:{}", result, e);
        }
        return ResponseEntity.ok(r);
    }

}
