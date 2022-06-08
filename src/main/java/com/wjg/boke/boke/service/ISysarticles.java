package com.wjg.boke.boke.service;

import com.wjg.boke.boke.po.SysArticles;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ISysarticles {

    List<SysArticles> selectAll(Integer page,Integer limit,Integer sort_id,Integer user_id);

    Integer selectCount(Integer sortId,Integer user_id);

    SysArticles selectById(Integer articlesId);

    List<SysArticles> selectWz(Integer page, Integer limit,Integer customerId);

    Integer selectCountColl(Integer customer_id);

    boolean UploadEs() throws IOException;

    int update(SysArticles record);

    //通过ES模糊搜索
    List<Map<String,Object>> selectByEs(String title) throws IOException;

    //新增
    boolean insert(SysArticles articles);

    //redis博客初始化
    void initialization() throws ParseException;
}
