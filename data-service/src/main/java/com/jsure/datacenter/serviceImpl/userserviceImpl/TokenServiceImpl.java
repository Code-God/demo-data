package com.jsure.datacenter.serviceImpl.userserviceImpl;

import com.google.common.collect.Maps;
import com.jsure.datacenter.check.InnerUserCheck;
import com.jsure.datacenter.constant.CustomConstant;
import com.jsure.datacenter.exception.CustomException;
import com.jsure.datacenter.innerservice.inneruserservice.InnerUserService;
import com.jsure.datacenter.mapper.TRoleMapper;
import com.jsure.datacenter.mapper.usermapper.TUserMapper;
import com.jsure.datacenter.model.model.TRole;
import com.jsure.datacenter.model.model.TUser;
import com.jsure.datacenter.model.enums.CustomErrorEnum;
import com.jsure.datacenter.model.param.TokenPram;
import com.jsure.datacenter.model.param.UserInfoParam;
import com.jsure.datacenter.model.param.UserParam;
import com.jsure.datacenter.model.result.TUserResult;
import com.jsure.datacenter.service.userservice.TokenService;
import com.jsure.datacenter.utils.BeanMapper;
import com.jsure.datacenter.utils.JWTUtil;
import com.jsure.datacenter.utils.MD5Util;
import com.jsure.datacenter.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/5/21
 * @Time: 15:09
 * I am a Code Man -_-!
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private InnerUserService innerUserService;

    @Autowired
    private TRoleMapper tRoleMapper;

    @Autowired
    private InnerUserCheck innerUserCheck;

    /**
     * 登录
     * 把token返回给客户端
     *
     * @return
     */
    @Override
    public Map<String, Object> login(TokenPram tokenPram) {
        Map<String, Object> resultMap = Maps.newHashMap();
        //根据用户名查询用户信息
        TUser user = innerUserService.findByUserName(tokenPram.getUsername(), true);
        //对密码加密
        tokenPram.setPassword(MD5Util.MD5Encrypt(tokenPram.getPassword()));
        //密码错误
        if (!tokenPram.getPassword().equals(user.getPassword())) {
            throw new CustomException(CustomErrorEnum.ERROR_CODE_341006.getErrorCode(),
                    CustomErrorEnum.ERROR_CODE_341006.getErrorDesc());
        } else {
            //生成token密钥
            String tokenKey = JWTUtil.createJWT(user.getId().toString(), user.getRoleId(), user.getUserName(), CustomConstant.JWT_TTL);
            resultMap.put("Authorization", tokenKey);
            resultMap.put("userId", user.getId());
        }
        return resultMap;
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param userName
     * @return
     */
    @Override
    public TUserResult findUserByUserName(String userName) {
        TUser user = innerUserService.findByUserName(userName,true);
        TUserResult rt = new TUserResult();
        BeanMapper.copy(user, rt);
        return rt;
    }

    /**
     * 根据角色id查询权限信息
     *
     * @param roleId
     * @return
     */
    @Override
    public TRole findPermissionByRoleId(Integer roleId) {
        return tRoleMapper.selectByPrimaryKey(roleId);
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Map<String, Object> updateUsers(UserInfoParam user) {
        Map<String, Object> resultMap = Maps.newHashMap();
        //判断是否有同名用户
        TUser re = innerUserService.findByUserName(user.getUserName(),false);
        if (ObjectUtils.isNotNullAndEmpty(re)) {
            throw new CustomException(CustomErrorEnum.ERROR_CODE_341012.getErrorCode(),
                    CustomErrorEnum.ERROR_CODE_341012.getErrorDesc());
        }
        TUser users = new TUser();
        BeanMapper.copy(user, users);
        if (ObjectUtils.isNotNullAndEmpty(users.getPassword())) {
            users.setPassword(MD5Util.MD5Encrypt(users.getPassword()));
        }
        //更新
        innerUserService.updateUsers(users);
        //返回更新后的数据
        TUser result = innerUserService.findUsesById(users.getId(),true);
        TUserResult rt = new TUserResult();
        BeanMapper.copy(result, rt);
        resultMap.put("user", rt);
        return resultMap;
    }

    /**
     * 创建用户
     *
     * @param userPram
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Map<String, Object> addUsers(UserInfoParam userPram) {
        Map<String, Object> resultMap = Maps.newHashMap();
        //判断是否有同名用户
        TUser re = innerUserService.findByUserName(userPram.getUserName(),false);
        if (ObjectUtils.isNotNullAndEmpty(re)) {
            throw new CustomException(CustomErrorEnum.ERROR_CODE_341012.getErrorCode(),
                    CustomErrorEnum.ERROR_CODE_341012.getErrorDesc());
        }
        TUser user = new TUser();
        BeanMapper.copy(userPram, user);
        user.setPassword(MD5Util.MD5Encrypt(userPram.getPassword()));
        user.setCreateDate(new Date());
        //创建用户
        innerUserService.addUsers(user);
        //返回创建后的用户信息
        TUser result = innerUserService.findByUserName(userPram.getUserName(),true);
        TUserResult rt = new TUserResult();
        BeanMapper.copy(result, rt);
        resultMap.put("user", rt);
        return resultMap;
    }

    /**
     * 删除用户
     *
     * @param usersId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Map<String, Object> deleteUsers(Integer[] usersId) {
        Map<String, Object> resultMap = Maps.newHashMap();
        Arrays.asList(usersId).stream().forEach(p -> {
            innerUserService.deleteUsers(p);
        });
        return resultMap;
    }

    /**
     * 查询用户列表信息 feign
     *
     * @param userPram
     * @return
     */
    @Override
    public List<TUserResult> findUserList(UserParam userPram) {
        innerUserCheck.checkUserParam(userPram);
        List<TUserResult> list = innerUserService.findUserList(userPram);
//        List<TUserResult> r = BeanMapper.mapList(list, TUserResult.class);
        return list;
    }


    /**
     * 创建用户 feign
     * @param userPram
     * @return
     */
    @Override
    public Map<String, Object> addUsers2(UserInfoParam userPram) {
        Map<String, Object> resultMap = Maps.newHashMap();
        //判断是否有同名用户
        TUser re = innerUserService.findByUserName(userPram.getUserName(),false);
        if (ObjectUtils.isNotNullAndEmpty(re)) {
            throw new CustomException(CustomErrorEnum.ERROR_CODE_341012.getErrorCode(),
                    CustomErrorEnum.ERROR_CODE_341012.getErrorDesc());
        }
        TUser user = new TUser();
        BeanMapper.copy(userPram, user);
        user.setPassword(MD5Util.MD5Encrypt(userPram.getPassword()));
        user.setCreateDate(new Date());
        //创建用户
        innerUserService.addUsers2(user);
        //返回创建后的用户信息
        TUser result = innerUserService.findByUserName(userPram.getUserName(),true);
        TUserResult rt = new TUserResult();
        BeanMapper.copy(result, rt);
        resultMap.put("user", rt);
        return resultMap;
    }
}
