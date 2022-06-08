package com.wjg.boke.boke.config;

import cn.hutool.core.map.MapUtil;
import com.wjg.boke.boke.shiro.AccountRealm;
import com.wjg.boke.boke.shiro.shiroFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sun.net.httpserver.AuthFilter;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class shiroConfig {

    //安全管理器
    @Bean
    public SecurityManager securityManager(AccountRealm accountRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(accountRealm);
        log.info("---------------------->securityManager注入成功");
        return securityManager;
    }

    //过滤器链
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
         ShiroFilterFactoryBean filterFactoryBean=new ShiroFilterFactoryBean();
         filterFactoryBean.setSecurityManager(securityManager);
        // 设置登录请求
        filterFactoryBean.setLoginUrl("/login");
        // 设置登录成功后的页面
        filterFactoryBean.setSuccessUrl("/");
        // 配置未授权跳转页面
        //filterFactoryBean.setUnauthorizedUrl("/error");
        //配置自定义拦截规则
        filterFactoryBean.setFilters(MapUtil.of("auth",authFilter()));
        //shiro的内置过滤器
        /*
           anon: 无需认证就可以访问
           authc: 必须认证了才能访问
           user： 必须拥有记住我 功能才能用
           perms： 拥有对某个资源的权限才能访问
           role： 拥有某个角色权限才能访问
        */
        Map<String,String> hashMap=new LinkedHashMap<>();
        hashMap.put("/user/**","auth");
        hashMap.put("/message/nums","auth");

        hashMap.put("/login","anon");
        //配置不需要拦截的路径
        filterFactoryBean.setFilterChainDefinitionMap(hashMap);
        return filterFactoryBean;
    }

    @Bean
    public shiroFilter authFilter(){
        return new shiroFilter();
    }
}
