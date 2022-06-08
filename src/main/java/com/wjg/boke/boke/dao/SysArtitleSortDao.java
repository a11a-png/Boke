package com.wjg.boke.boke.dao;

import com.wjg.boke.boke.po.SysArtitleSort;

public interface SysArtitleSortDao {
    int deleteByPrimaryKey(Integer articleId);

    int insert(SysArtitleSort record);

    int insertSelective(SysArtitleSort record);

    SysArtitleSort selectByPrimaryKey(Integer articleId);

    int updateByPrimaryKeySelective(SysArtitleSort record);

    int updateByPrimaryKey(SysArtitleSort record);
}