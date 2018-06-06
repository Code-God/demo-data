package com.jsure.datacenter.filter;


import com.google.common.collect.Maps;
import com.jsure.datacenter.jwtshiro.JWTToken;
import com.jsure.datacenter.model.token.CheckResult;
import com.jsure.datacenter.utils.JSONUtil;
import com.jsure.datacenter.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @Author: wuxiaobiao
 * @Description: token验签
 * @Date: Created in 2018/5/22
 * @Time: 14:00
 * I am a Code Man -_-!
 */
@Slf4j
public class LoginAuthFilter extends FormAuthenticationFilter {

    /**
     * 对token进行验签
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Map<String, Object> map = Maps.newHashMap();
        //设置响应头
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json");
        //获取请求头的token
        String token = httpRequest.getHeader("Authorization");
        log.info("call auth token, token:{}", token);
        // token验签
        CheckResult r = JWTUtil.validateJWT(token);
        boolean flag = false;
        //1.token验签通过后,提交给realm进行登入
        //2.token验签不通过则直接输出
        if (r.isSuccess()) {
            JWTToken jwtToken = new JWTToken(token);
            getSubject(request, response).login(jwtToken);
            flag = true;
        } else {
            map.put("resCode", r.getErrCode());
            map.put("resMsg", r.getErrMsg());
            //写回给客户端
            PrintWriter out = httpResponse.getWriter();
            out.write(JSONUtil.obj2json(map));
            //刷新和关闭输出流
            out.flush();
            out.close();
            log.info("call auth token,URI:{}, RESULT:{}", httpRequest.getRequestURI(),map);
        }
        return flag;
    }
}
