package com.jsure.datacenter.jwtshiro;


import com.jsure.datacenter.filter.LoginAuthFilter;
import com.jsure.datacenter.filter.RestFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: wuxiaobiao
 * @Description: Shiro 配置
 * @Date: Created in 2018/5/24
 * @Time: 13:45
 * I am a Code Man -_-!
 */
@Configuration
public class ShiroConfig {

    /**
     * 安全管理器
     * @return
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager getManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自定义realm
        manager.setRealm(getShiroRealm());
        // 关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);
        return manager;
    }

    /**
     * 自定义reaml
     * @return
     */
    @Bean("shiroRealm")
    public ShiroRealm getShiroRealm(){
        ShiroRealm realm = new ShiroRealm();
        return realm;
    }

    /**
     * 自定义过滤器
     * @param securityManager
     * @return
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 添加自定义过滤器 所有请求通过自定义的Filter
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("restFilter", new RestFilter());
        filterMap.put("authToken", new LoginAuthFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        // 自定义url规则
        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        // 开放swagger
//        filterRuleMap.put("/swagger-ui.html", "anon");
//        filterRuleMap.put("/swagger-resources", "anon");
//        filterRuleMap.put("/v2/api-docs", "anon");
//        filterRuleMap.put("/swagger-resources/configuration/**", "anon");
//        filterRuleMap.put("/webjars/springfox-swagger-ui/**", "anon");
        filterRuleMap.put("/swagger-ui.html","anon");
        filterRuleMap.put("/swagger/**","anon");
        filterRuleMap.put("/webjars/**", "anon");
        filterRuleMap.put("/swagger-resources/**","anon");
        filterRuleMap.put("/docs.html","anon");
        filterRuleMap.put("/v2/**","anon");
        filterRuleMap.put("/favicon.ico","anon");
        // 开放druid
        filterRuleMap.put("/druid/**", "anon");
        // 开放登录
        filterRuleMap.put("/bc/login", "restFilter,anon");
        filterRuleMap.put("/bc/logout", "restFilter,anon");
        // 拦截所以请求
        filterRuleMap.put("/**", "restFilter,authToken");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    /**
     * 添加注解支持
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
