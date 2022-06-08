package com.wjg.boke.boke.service;

import com.wjg.boke.boke.im.vo.mineUser;
import com.wjg.boke.boke.po.SysCustomer;
import com.wjg.boke.boke.shiro.shiroFile;

public interface ISysCustomer {

    Integer insert(SysCustomer user);

    shiroFile selectByPrimaryKey(Integer userid, String userIP, String password);

    mineUser getmineUser();
}
