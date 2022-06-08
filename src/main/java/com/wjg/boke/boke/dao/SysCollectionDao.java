package com.wjg.boke.boke.dao;

import com.wjg.boke.boke.po.SysCollection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysCollectionDao {
    int deleteByPrimaryKey(Integer collectionId);

    int insert(SysCollection record);

    int insertSelective(SysCollection record);

    SysCollection selectByPrimaryKey(Integer collectionId);

    int updateByPrimaryKeySelective(SysCollection record);

    int updateByPrimaryKey(SysCollection record);

    Integer selectCount(@Param("customer_id") Integer customer_id);
}