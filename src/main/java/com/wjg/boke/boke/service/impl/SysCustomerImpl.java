package com.wjg.boke.boke.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.wjg.boke.boke.dao.SysCustomerDao;
import com.wjg.boke.boke.im.vo.mineUser;
import com.wjg.boke.boke.po.SysCustomer;
import com.wjg.boke.boke.service.ISysCustomer;
import com.wjg.boke.boke.shiro.shiroFile;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysCustomerImpl implements ISysCustomer {

    @Resource
    public SysCustomerDao sysCustomerDao;

    @Override
    public Integer insert(SysCustomer user) {
        return sysCustomerDao.insert(user);
    }

    @Override
    public shiroFile selectByPrimaryKey(Integer userid, String userIP,String password) {
        SysCustomer customer= sysCustomerDao.selectByPrimaryKey(userid,userIP);
        if (customer==null){
            throw new UnknownAccountException();
        }else if (!customer.getUserPassword().equals(password)) {
            throw new IncorrectCredentialsException();
        }

        shiroFile shirodata=new shiroFile();
        //反射赋值
        BeanUtil.copyProperties(customer,shirodata);

        return shirodata;
    }

    //获取用户（用于消息聊天）
    @Override
    public mineUser getmineUser(){
        mineUser minuser=new mineUser();
        shiroFile user= (shiroFile)SecurityUtils.getSubject().getPrincipal();
        if (user!=null){
            minuser.setId(user.getUserid());
            minuser.setAvatar(user.getUserPhoto());
            minuser.setStatus("online");
            minuser.setUsername(user.getUserName());
        }else{
            Integer imuserID=(Integer) SecurityUtils.getSubject().getSession().getAttribute("imUserId");
            minuser.setId(imuserID!=null? imuserID: RandomUtil.randomInt());
            SecurityUtils.getSubject().getSession().setAttribute("imUserId",minuser.getId());
            //匿名用户
            minuser.setAvatar("http://tp1.sinaimg.cn/5619439268/180/40030060651/1");
            minuser.setStatus("online");
            minuser.setUsername("匿名用户");
        }
        return minuser;
    }
}
