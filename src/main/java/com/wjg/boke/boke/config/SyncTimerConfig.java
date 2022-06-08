package com.wjg.boke.boke.config;

import com.wjg.boke.boke.po.SysArticles;
import com.wjg.boke.boke.service.ISysarticles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

//同步数据
@Component
@Slf4j
public class SyncTimerConfig {

    @Autowired
    public RedisTemplate redisTemplate;
    @Autowired
    public ISysarticles sysarticles;

    @Scheduled(cron = "0/5 * * * * *")
    public void task(){
        //获取缓存数据
        Set<String> strarr = redisTemplate.boundHashOps("viewCount").keys();
        //分隔字符串
        for (String str:strarr) {
            Integer postid=Integer.valueOf(str.replace("post",""));
            //获取缓存数据
            Integer viewcount=(Integer)redisTemplate.boundHashOps("viewCount").get(str);
            //查询对应的文章
            SysArticles articles=new SysArticles();
            //进行修改
            articles.setArticlesViews(viewcount);
            articles.setArticlesId(postid);
            //修改
            int bo=sysarticles.update(articles);
            if (bo>0){
                log.info("文章"+postid+"同步成功");
                //删除缓存
                redisTemplate.boundHashOps("viewCount").delete("post"+postid);
            }else{
                log.info("文章"+postid+"同步失败");
            }
        }
    }
}
