package com.wjg.boke.boke.dao;

import com.wjg.boke.boke.po.SysUserFriends;

public interface SysUserFriendsDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserFriends record);

    int insertSelective(SysUserFriends record);

    SysUserFriends selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserFriends record);

    int updateByPrimaryKey(SysUserFriends record);
}