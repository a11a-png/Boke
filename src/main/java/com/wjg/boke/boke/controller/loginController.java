package com.wjg.boke.boke.controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.StrUtil;
import com.wjg.boke.boke.comment.page;
import com.wjg.boke.boke.po.SysArticles;
import com.wjg.boke.boke.po.SysCustomer;
import com.wjg.boke.boke.service.ISysCustomer;
import com.wjg.boke.boke.service.ISysarticles;
import com.wjg.boke.boke.util.ValidationUtil;
import com.wjg.boke.boke.vo.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class loginController{

    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    @Autowired
    public ISysarticles sysarticles;
    @Autowired
    public ISysCustomer sysCustomer;

    //index页面
    @RequestMapping("/")
    public String index(Integer curr){
        page<SysArticles> data=new page<SysArticles>();
        if (request.getAttribute("itemID")==null){
            request.setAttribute("itemID",0);
        }
        data.setTotal(sysarticles.selectCount(null,null));
        data.setUserList(sysarticles.selectAll(2,curr!=null?curr:1,null,null));
        data.setCurrent(curr!=null?curr:1);
        data.setPage(2);
        request.setAttribute("articles",data);
        return "index";
    }

    //验证码
    @GetMapping("/getYzm")
    public void getYzm(@RequestParam("time") Long time) throws IOException {
        // 随机生成 4 位数
        RandomGenerator randomGenerator=new RandomGenerator("0123456789",4);
        //设置图片大小
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200,40);
        response.setContentType("image/jepg");
        response.setHeader("Pragma","No-cache");
        lineCaptcha.setGenerator(randomGenerator);
        //将验证码存到session中
        request.getSession().setAttribute("YZM",lineCaptcha.getCode());
        //输出流
        lineCaptcha.write(response.getOutputStream());
        //关闭流
        response.getOutputStream().close();
    }

    //注册页面
    @GetMapping("/reg")
    public String reg(){
        return "/auth/reg";
    }

    //注册用户
    @PostMapping("/creatCustomer")
    @ResponseBody
    public Result creatCustomer(SysCustomer user, String repass, String vercode){
          //ValidationUtil工具类
          //判断是否为空
          ValidationUtil.ValidResult validResult=ValidationUtil.validateBean(user);
          if (validResult.hasErrors()){
              return Result.fail(validResult.getErrors());
          }
          //判断密码是否输入一致
          if (!user.getUserPassword().equals(repass)){
              return Result.fail("两次输入密码不一致");
          }
          //判断验证码
          if (!vercode.equals(request.getSession().getAttribute("YZM"))){
              return Result.fail("验证码输入不一致");
          }
          //进行创建
          sysCustomer.insert(user);
          return Result.success("注册成功",null).action("/login");
    }

    //登录页面
    @GetMapping("/login")
    public String login(){
        return "/auth/login";
    }

    //mods/index.js封装的fly方法,若form表单没有指定action则以页面作为请求路径
    //登录用户
    @PostMapping("/loginDl")
    @ResponseBody
    public Result loginDl(SysCustomer user){
        //判断是否为空
        if (!StrUtil.isNotEmpty(user.getUserip()) || !StrUtil.isNotEmpty(user.getUserPassword())){
            return Result.fail("账号或密码不能为空");
        }
        //使用shiro进行登录认证
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUserip(), user.getUserPassword());
        try{
            //进行验证登录
            SecurityUtils.getSubject().login(token);
        }catch (AuthenticationException e){
            if (e instanceof UnknownAccountException) {
                return Result.fail("用户不存在");
            } else if (e instanceof LockedAccountException) {
                return Result.fail("用户被禁用");
            } else if (e instanceof IncorrectCredentialsException) {
                return Result.fail("密码错误");
            } else {
                return Result.fail("用户认证失败");
            }
        }
        return Result.success("登录成功",null).action("/");
    }

    //退出登录
    @RequestMapping("/logOut")
    public String logOut(){
        SecurityUtils.getSubject().logout();
        //重定向到登录页面
        return "redirect:/";
    }

}
