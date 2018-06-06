package com.jsure.datacenter.jwtshiro;


import com.jsure.datacenter.mapper.TRoleMapper;
import com.jsure.datacenter.mapper.usermapper.TUserMapper;
import com.jsure.datacenter.model.model.TRole;
import com.jsure.datacenter.model.model.TUser;
import com.jsure.datacenter.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/5/24
 * @Time: 13:43
 * I am a Code Man -_-!
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {


    @Autowired
    private TUserMapper tUserMapper;

    @Autowired
    private TRoleMapper tRoleMapper;


    /**
     * 必须重写此方法，不然Shiro会报错
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，
     * 调用checkRole,checkPermission时触发
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JWTUtil.getUsername(principals.toString());
        TUser user = tUserMapper.findByUserName(username);
        TRole tRole = tRoleMapper.selectByPrimaryKey(user.getRoleId());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> permission = new HashSet<>(Arrays.asList(tRole.getPermission().split(",")));
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证token
     *
     * @param auth
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) {
        //获取token
        String token = (String) auth.getCredentials();
        //通过shiro认证
        return new SimpleAuthenticationInfo(token, token, "shiro_realm");
    }
}
