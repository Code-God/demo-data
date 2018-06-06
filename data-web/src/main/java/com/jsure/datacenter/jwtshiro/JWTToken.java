package com.jsure.datacenter.jwtshiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author: wuxiaobiao
 * @Description: token
 * @Date: Created in 2018/5/24
 * @Time: 13:42
 * I am a Code Man -_-!
 */
public class JWTToken implements AuthenticationToken {

    private static final long serialVersionUID = -3258717655586217510L;

    // 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}

