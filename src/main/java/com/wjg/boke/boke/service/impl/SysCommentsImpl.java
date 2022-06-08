package com.wjg.boke.boke.service.impl;

import com.wjg.boke.boke.dao.SysArticlesDao;
import com.wjg.boke.boke.dao.SysCommentsDao;
import com.wjg.boke.boke.dao.SysMessageDao;
import com.wjg.boke.boke.po.SysArticles;
import com.wjg.boke.boke.po.SysComments;
import com.wjg.boke.boke.po.SysMessage;
import com.wjg.boke.boke.service.ISysComments;
import com.wjg.boke.boke.service.IWsservice;
import com.wjg.boke.boke.shiro.shiroFile;
import com.wjg.boke.boke.vo.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class SysCommentsImpl implements ISysComments {

    @Resource
    public SysCommentsDao commentsDao;
    @Resource
    public SysMessageDao sysMessageDao;
    @Resource
    public SysArticlesDao articlesDao;
    @Autowired
    public IWsservice wsservice;
    @Autowired
    public RedisTemplate redisTemplate;

    @Override
    public List<SysComments> selectAll(Integer curr, Integer page, Integer articleId) {
        int current=(curr-1)*page;
        return commentsDao.selectAll(current,page,articleId);
    }

    @Override
    public Integer selectCount(Integer articleId) {
        return commentsDao.selectCount(articleId);
    }

    //新增评论
    @Transactional  //事务
    @Override
    public Result insert(SysMessage message) throws ParseException {
        SysComments comments=new SysComments();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        SimpleDateFormat dateFormat2=new SimpleDateFormat("yyyy-mm-dd");
        String now=dateFormat2.format(new Date());
        Calendar c=Calendar.getInstance();
        int bot=0;
        //获取登录用户信息
        shiroFile DLuser=(shiroFile) SecurityUtils.getSubject().getPrincipal();
        //判断是否自己回复自己
        if (DLuser.getUserid()==message.getFromuserid()){
            return Result.fail("不能回复自己的评论");
        }
        //普通评论
        message.setType((byte)1);
        //判断是否为回复评论
        if (message.getCommentid()!=null){
            //推送消息给被评论人
            //判断是否回复信息给该发布文章的作者
            if (message.getTouserid()!=message.getPostUserId()){
                //不是则通知
                wsservice.sendMessage(message.getTouserid());
            }
            //若是则覆盖设置为回复信息
            message.setType((byte)2);
            //设置父评论ID
            comments.setParentCommentId(message.getCommentid());
        }else{
           message.setTouserid(message.getPostUserId());
        }

        message.setStatus((byte)1); //未读
        message.setMessDate(c.getTime()); //评论信息
        message.setFromuserid(DLuser.getUserid()); //发送人ID
        //新增
        sysMessageDao.insert(message);
        //新增评论表
        comments.setUserId(Long.valueOf(DLuser.getUserid()));
        comments.setArticleId(Long.valueOf(message.getPotsid()));
        comments.setLikeCount(Long.valueOf(0));
        comments.setCommentDate(c.getTime());
        comments.setCommentContent(message.getMessage());
        commentsDao.insert(comments);
        //修改评论数
        SysArticles articles=new SysArticles();
        bot=articlesDao.updateCount(message.getPotsid());
        if (bot==0){
            return Result.fail("评论失败");
        }
        //推送消息给发布文章作者
        wsservice.sendMessage(message.getPostUserId());
        Set<String> dt = redisTemplate.boundZSetOps(now).range(0,-1);
        //添加缓存数据
        if (dt.size()>0){
          for (String a:dt) {
            if (!a.equals(String.valueOf(message.getPotsid()))){
                //没有则添加
                redisTemplate.boundZSetOps(now).add(String.valueOf(message.getPotsid()),1);
            }else{
                //有则自增
                redisTemplate.boundZSetOps(now).incrementScore(String.valueOf(message.getPotsid()),1);
            }
          }
        }else{
            //没有则添加
            redisTemplate.boundZSetOps(now).add(String.valueOf(message.getPotsid()),1);
        }
        return Result.success("评论成功").action("/category/jie/"+message.getPotsid());
    }

    @Override
    public List<SysComments> selectBydate(String startDate, String enddate) {
        return commentsDao.selectBydate(startDate,enddate);
    }
}
