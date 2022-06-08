package com.wjg.boke.boke.shiro;

import com.wjg.boke.boke.po.SysCustomer;
import com.wjg.boke.boke.service.impl.SysCustomerImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//shiro授权
@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    public SysCustomerImpl sysCustomer;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //验证身份信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        //进行sql登录
        shiroFile customer = sysCustomer.selectByPrimaryKey(null,token.getUsername(),String.valueOf(token.getPassword()));

        SecurityUtils.getSubject().getSession().setAttribute("customer",customer);

        //简单的身份验证信息
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(customer,token.getCredentials(),getName());
        return info;
    }
}
