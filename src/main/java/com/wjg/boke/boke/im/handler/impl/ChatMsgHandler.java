package com.wjg.boke.boke.im.handler.impl;

import cn.hutool.json.JSONUtil;
import com.wjg.boke.boke.im.handler.MsgHandler;
import com.wjg.boke.boke.im.handler.filter.Chatfilter;
import com.wjg.boke.boke.im.message.ChatImMess;
import com.wjg.boke.boke.im.message.ChatOutMess;
import com.wjg.boke.boke.im.vo.ImMess;
import com.wjg.boke.boke.im.vo.ImTo;
import com.wjg.boke.boke.im.vo.mineUser;
import com.wjg.boke.boke.service.IChat;
import com.wjg.boke.boke.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;

import java.util.Date;

@Slf4j
public class ChatMsgHandler implements MsgHandler {

    @Autowired
    public IChat chat;

    @Override
    public void handler(String data, WsRequest wsRequest, ChannelContext context) {
        ChatImMess chatImMess= JSONUtil.toBean(data,ChatImMess.class);

        mineUser mine= chatImMess.getMine();  //发送方
        ImTo to= chatImMess.getTo();  //接收方

        //特殊处理 -- 如：特殊字符检查

        //将mine发送方与to接收方的消息体，结合起来成为输出消息体
        ImMess  imMess=new ImMess();
        imMess.setContent(mine.getContent());
        imMess.setAvatar(mine.getAvatar());
        imMess.setMine(false); // 是否是我发送的消息
        imMess.setUsername(mine.getUsername());
        imMess.setFromid(mine.getId());
        imMess.setId(999);
        imMess.setTimestamp(new Date());
        imMess.setType(to.getType());

        //输出消息体
        ChatOutMess chatOutMess=new ChatOutMess();
        chatOutMess.setEmit("chatMessage");
        chatOutMess.setData(imMess);

        String result=JSONUtil.toJsonStr(chatOutMess);
        log.info("群聊消息----------> {}", result);

        //设置当前消息通过，用来进行消息发送过滤
        Chatfilter chatfilter=new Chatfilter();
        chatfilter.setCurrentContext(context);

        WsResponse wsResponse=WsResponse.fromText(result,"utf-8");
        Tio.sendToGroup(context.getGroupContext(),"e-group-study", wsResponse,chatfilter);

        //保存消息
        IChat chat= (IChat)SpringUtil.getBean("chatImpl");
        chat.setGroupHistoryMsg(imMess);
    }
}
