package com.wjg.boke.boke.dao;

import com.wjg.boke.boke.po.SysCustomer;
import org.apache.ibatis.annotations.Param;

public interface SysCustomerDao {
    int deleteByPrimaryKey(Integer userid);

    int insert(SysCustomer record);

    int insertSelective(SysCustomer record);

    SysCustomer selectByPrimaryKey(@Param("userid") Integer userid,@Param("userIP") String userIP);

    int updateByPrimaryKeySelective(SysCustomer record);

    int updateByPrimaryKey(SysCustomer record);
}