package com.wjg.boke.boke.dao;

import com.wjg.boke.boke.po.SysSort;

public interface SysSortDao {
    int deleteByPrimaryKey(Integer sortId);

    int insert(SysSort record);

    int insertSelective(SysSort record);

    SysSort selectByPrimaryKey(Integer sortId);

    int updateByPrimaryKeySelective(SysSort record);

    int updateByPrimaryKey(SysSort record);
}