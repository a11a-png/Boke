package com.wjg.boke.boke.controller;

import cn.hutool.json.JSONArray;
import com.wjg.boke.boke.comment.reqcom;
import com.wjg.boke.boke.po.SysArticles;
import com.wjg.boke.boke.po.SysMenu;
import com.wjg.boke.boke.po.SysMessage;
import com.wjg.boke.boke.service.ISysComments;
import com.wjg.boke.boke.service.ISysarticles;
import com.wjg.boke.boke.service.ISysmenu;
import com.wjg.boke.boke.service.ISysmessage;
import com.wjg.boke.boke.shiro.shiroFile;
import com.wjg.boke.boke.vo.Result;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

//文章页面 控制器
@Controller
public class PostController {

    //获取用户登录信息
    @Autowired
    public reqcom com;
    @Autowired
    HttpServletRequest request;
    @Autowired
    public ISysarticles sysarticles;
    @Autowired
    public ISysComments sysComments;
    @Autowired
    public ISysmessage sysmessage;
    @Autowired
    public ISysmenu sysmenu;
    //mq发送消息类
    @Autowired
    public RabbitTemplate rabbitTemplate;
    @Autowired
    public RedisTemplate redisTemplate;


    //菜单页面跳转
    @RequestMapping("/category/{id}")
    public String category(@PathVariable("id") Integer id){
        //设置请求值
        int curr = ServletRequestUtils.getIntParameter(request,"curr",1);
        request.setAttribute("itemID",id);
        request.setAttribute("sortId",id);
        request.setAttribute("curr",curr);
        return "/post/category";
    }

    //评论页面
    @RequestMapping("/category/jie/{id}")
    public String jie(@PathVariable("id") Integer id){
        SysArticles articles=sysarticles.selectById(id);
        //断言，判断是否查询成功
        Assert.notNull(articles,"文章已被删除");
        int curr = ServletRequestUtils.getIntParameter(request,"curr",1);
        request.setAttribute("articleId",articles.getArticlesId());
        request.setAttribute("curr",curr);
        request.setAttribute("itemID",articles.getSortId());
        //缓存数据
        Integer viewCount = (Integer)redisTemplate.boundHashOps("viewCount").get("post"+articles.getArticlesId());
        if (viewCount==null || viewCount==0){
            //判断是否存在，若不存在则 articles.getArticlesViews（自身）+1
            articles.setArticlesViews(articles.getArticlesViews()+1);
        }else{
            //判断是否存在，若存在则：viewCount+1
            articles.setArticlesViews(viewCount+1);
        }
        //将数据更新到缓存中
        redisTemplate.boundHashOps("viewCount").put("post"+articles.getArticlesId(),articles.getArticlesViews());
        request.setAttribute("postData",articles);
        return "/post/detail";
    }

    //评论
    @RequestMapping("/user/comment")
    @ResponseBody
    public Result comment(SysMessage message) throws ParseException {
       return sysComments.insert(message);
    }

    //消息通知
    @PostMapping("/message/nums")
    @ResponseBody
    public Result nums(){
       shiroFile user=com.getshiroFile();
       int count=sysmessage.selectWD(user.getUserid());
       Result result=new Result();
       result.setCount(count);
       result.setStatus(0);
       return result;
    }

    //ES搜索
    @RequestMapping("/SearchEs/{title}")
    @ResponseBody
    public Result SearchEs(@PathVariable("title") String title) throws IOException {
        request.setAttribute("title",title);
        return Result.success(title);
    }

    //查询页面
    @RequestMapping("/searchIndex/{title}")
    public String searchIndex(@PathVariable("title")String title){
        request.setAttribute("title",title);
        request.setAttribute("itemID",0);
        return "searchIndex";
    }

    //发表页面
    @GetMapping("/post/edit")
    public String edit(){
        List<SysMenu> data=sysmenu.selectmenu();
        request.setAttribute("menuList",data);
        return "post/edit";
    }

    //新增文章
    @PostMapping("/post/addpost")
    @ResponseBody
    public Result addpost(SysArticles articles){
       shiroFile user=(shiroFile) request.getSession().getAttribute("customer");
       articles.setUserId(user.getUserid());
       articles.setArticlesCount(0);
       articles.setArticlesViews(0);
       articles.setLikeCount(0);
       articles.setArticlesCover("http://localhost:8080/images/cover.jpg");
       Calendar c=Calendar.getInstance();
       articles.setArticlesDate(c.getTime());
       boolean bt=sysarticles.insert(articles);
       if (bt){
           //使用mq发送消息
           CorrelationData correlationData=new CorrelationData("1");
           rabbitTemplate.convertAndSend("esexchange","keyvalue","新增",correlationData);
           return Result.success().action("/");
       }
       return Result.fail("发表失败");
    }
}
