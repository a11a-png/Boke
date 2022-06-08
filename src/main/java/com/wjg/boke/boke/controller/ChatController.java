package com.wjg.boke.boke.controller;

import cn.hutool.core.map.MapUtil;
import com.wjg.boke.boke.im.vo.ImMess;
import com.wjg.boke.boke.im.vo.mineUser;
import com.wjg.boke.boke.service.IChat;
import com.wjg.boke.boke.service.ISysCustomer;
import com.wjg.boke.boke.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    public ISysCustomer sysCustomer;
    @Autowired
    public IChat chat;

    //打开聊天窗口 获取的用户信息
    @RequestMapping("/getMineAndGroupData")
    @ResponseBody
   public Result getChatData(){
        Map<String,Object> data=new HashMap<>();
        data.put("name","聊天窗口");  //群名
        data.put("type","group");  //群聊类型
        data.put("avatar","http://tp1.sinaimg.cn/5619439268/180/40030060651/1");   //群头像
        data.put("id",999);   //id
        data.put("members",0);  //群聊人数

        //获取用户信息
        mineUser user= sysCustomer.getmineUser();

        return Result.success(MapUtil.builder()
                              .put("group",data)
                              .put("mine",user)
                              .map());
   }


    //查看聊天记录
    @RequestMapping("/getGroupHistoryMsg")
    @ResponseBody
    public Result getGroupHistoryMsg(){
        return Result.success(chat.getGroupHistoryMsg());
   }
}
