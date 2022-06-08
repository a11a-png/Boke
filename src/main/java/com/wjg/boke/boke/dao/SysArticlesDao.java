package com.wjg.boke.boke.dao;

import com.wjg.boke.boke.po.SysArticles;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysArticlesDao {
    int deleteByPrimaryKey(Integer articlesId);

    int insert(SysArticles record);

    SysArticles selectByPrimaryKey(Integer articlesId);

    int update(SysArticles record);

    int updateByPrimaryKey(SysArticles record);

    List<SysArticles> selectAll(@Param("page")Integer page, @Param("lit")Integer limit,@Param("sort_id")Integer sort_id,@Param("user_id") Integer user_id);

    Integer selectCount(@Param("sortId") Integer sortId,@Param("user_id") Integer user_id);

    List<SysArticles> selectWz(@Param("page")Integer page, @Param("lit")Integer limit,@Param("customerId") Integer customerId);

    int updateCount(@Param("articlesId") Integer articlesId);
}