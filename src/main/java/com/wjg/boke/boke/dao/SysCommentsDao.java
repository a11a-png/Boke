package com.wjg.boke.boke.dao;

import com.wjg.boke.boke.comment.page;
import com.wjg.boke.boke.po.SysComments;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SysCommentsDao {
    int deleteByPrimaryKey(Integer commentId);

    int insert(SysComments record);

//    SysComments selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(SysComments record);

    int updateByPrimaryKey(SysComments record);

    List<SysComments> selectAll(@Param("curr") Integer curr, @Param("page")Integer page, @Param("articleId")Integer articleId);

    Integer selectCount(@Param("articleId") Integer articleId);

    //根据日期查询评论
    List<SysComments> selectBydate(@Param("startDate")String startDate,@Param("enddate")String enddate);
}