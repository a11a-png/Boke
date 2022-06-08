package com.wjg.boke.boke.templete;

import com.wjg.boke.boke.comment.templates.DirectiveHandler;
import com.wjg.boke.boke.comment.templates.TemplateDirective;
import com.wjg.boke.boke.po.SysArticles;
import com.wjg.boke.boke.service.ISysarticles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//解决重复代码
@Component
public class postsTempleteSeven extends TemplateDirective {

    @Autowired
    public RedisTemplate redisTemplate;
    @Autowired
    public ISysarticles sysarticles;

    @Override
    public String getName() {
        return "postsSix";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        //获取redis缓存数据
        Set<String> arr=redisTemplate.boundZSetOps("count").reverseRange(0,9);
        List<SysArticles> arrlist=new ArrayList<>();
        for (String z:arr){
            //查询前10热议的论文信息
            SysArticles articles=(SysArticles)redisTemplate.boundValueOps(z).get();
            arrlist.add(articles);
        }
        handler.put(HOTARTICLE, arrlist).render();
    }
}
