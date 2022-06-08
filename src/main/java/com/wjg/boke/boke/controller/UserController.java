package com.wjg.boke.boke.controller;

import com.wjg.boke.boke.comment.page;
import com.wjg.boke.boke.comment.reqcom;
import com.wjg.boke.boke.po.SysComments;
import com.wjg.boke.boke.po.SysMessage;
import com.wjg.boke.boke.service.ISysarticles;
import com.wjg.boke.boke.service.ISysmessage;
import com.wjg.boke.boke.service.impl.SysmessageImpl;
import com.wjg.boke.boke.shiro.shiroFile;
import com.wjg.boke.boke.vo.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    //获取用户登录信息
    @Autowired
    public reqcom com;
    @Autowired
    public HttpServletRequest request;
    @Autowired
    public ISysmessage sysmessage;
    @Autowired
    public ISysarticles sysarticles;

    @GetMapping("/user/index")
    public String index(){
        shiroFile user=com.getshiroFile();
        int curr = ServletRequestUtils.getIntParameter(request,"curr",1);
        request.setAttribute("userid",user.getUserid());
        request.setAttribute("curr",curr);
        return "user/index";
    }

    @PostMapping("/collection/find/")
    @ResponseBody
    public Result collectionFind(){
      return Result.success(null,null);
    }

    @GetMapping("/user/mess")
    public String mess(){
        shiroFile user=com.getshiroFile();
        //设置已读消息
        sysmessage.updateYD(user.getUserid());
        int curr = ServletRequestUtils.getIntParameter(request,"curr",1);
        request.setAttribute("curr",curr);
        request.setAttribute("userid",user.getUserid());
        return "user/mess";
    }

    //将数据同步到ES中
    @GetMapping("/UploadES")
    @ResponseBody
    public Result UploadES() throws IOException {
         boolean bt=sysarticles.UploadEs();
         if (bt){
             return Result.success();
         }
         return Result.fail("同步失败");
    }
}
