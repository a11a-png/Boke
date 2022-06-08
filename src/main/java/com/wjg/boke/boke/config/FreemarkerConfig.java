package com.wjg.boke.boke.config;

import com.jagregory.shiro.freemarker.ShiroTags;
import com.wjg.boke.boke.templete.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

//设置配置类信息，将自定义模板引入到前端
@Configuration
public class FreemarkerConfig {

     @Autowired
     private freemarker.template.Configuration configuration;

     @Autowired
     postsTemplete posts;
     @Autowired
     postsTempleteTwo postsTwo;
    @Autowired
    postsTempleteThree postsThree;
    @Autowired
    postsTempleteFour postsFour;
    @Autowired
    postsTempleteFive postsFive;
    @Autowired
    postsTempleteSix postsSix;
    @Autowired
    postsTempleteSeven postsSeven;

     //引入freemark页面使用的组件库
     @PostConstruct
     public void setUp(){
         configuration.setSharedVariable("timeAgo",new TimeAgoMethod());
         configuration.setSharedVariable("posts",posts);
         configuration.setSharedVariable("postsTwo",postsTwo);
         configuration.setSharedVariable("postsThree",postsThree);
         configuration.setSharedVariable("postsFour",postsFour);
         configuration.setSharedVariable("postsFive",postsFive);
         configuration.setSharedVariable("postsSix",postsSix);
         configuration.setSharedVariable("postsSeven",postsSeven);
         //引入shiro标签库
         configuration.setSharedVariable("shiro",new ShiroTags());
     }
}
