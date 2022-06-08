package com.wjg.boke.boke.shiro;

import cn.hutool.json.JSONUtil;
import com.wjg.boke.boke.vo.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
//自定义shiro过滤器规则
public class shiroFilter extends UserFilter {

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest req=(HttpServletRequest)request;
        //ajax 请求
        String header=req.getHeader("X-Requested-With");
        if (header!=null && header.equals("XMLHttpRequest")){
            //判断是否未登录
            Boolean authen= SecurityUtils.getSubject().isAuthenticated();
            if (!authen){
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print(JSONUtil.toJsonStr(Result.fail("请先登录！")));
            }
        }else{
            // 若不是ajax请求则重定向到login登录页面
            super.redirectToLogin(request, response);
        }
    }
}
