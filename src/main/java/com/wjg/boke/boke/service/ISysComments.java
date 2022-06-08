package com.wjg.boke.boke.service;

import com.wjg.boke.boke.comment.page;
import com.wjg.boke.boke.po.SysComments;
import com.wjg.boke.boke.po.SysMessage;
import com.wjg.boke.boke.vo.Result;
import org.apache.ibatis.annotations.Param;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ISysComments {

    List<SysComments> selectAll(Integer curr, Integer page, Integer articleId);

    Integer selectCount(Integer articleId);

    Result insert(SysMessage message) throws ParseException;

    //根据日期查询评论
    List<SysComments> selectBydate(String startDate,String enddate);
}
