package com.wjg.boke.boke.comment;

import com.wjg.boke.boke.shiro.shiroFile;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class reqcom {

    public shiroFile getshiroFile(){
        shiroFile user=new shiroFile();
        if (SecurityUtils.getSubject().getPrincipal()!=null){
            user = (shiroFile) SecurityUtils.getSubject().getPrincipal();
        }
        return user;
    }
}
