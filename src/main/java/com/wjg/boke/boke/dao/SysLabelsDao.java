package com.wjg.boke.boke.dao;

import com.wjg.boke.boke.po.SysLabels;

public interface SysLabelsDao {
    int deleteByPrimaryKey(Integer labelId);

    int insert(SysLabels record);

    int insertSelective(SysLabels record);

    SysLabels selectByPrimaryKey(Integer labelId);

    int updateByPrimaryKeySelective(SysLabels record);

    int updateByPrimaryKey(SysLabels record);
}