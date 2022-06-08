package com.wjg.boke.boke.im.handler;

import com.wjg.boke.boke.im.handler.impl.ChatMsgHandler;
import com.wjg.boke.boke.im.handler.impl.PingMsgHandler;

import java.util.HashMap;
import java.util.Map;

public class MsgHandlerFactory {

    private static Map<String,MsgHandler> handlerMap=new HashMap<>();

    public static void init(){
        //添加消息类型
        handlerMap.put("chatMessage",new ChatMsgHandler());
        handlerMap.put("pingMessage",new PingMsgHandler());
    }

    public static MsgHandler getMsgHandler(String type){
        return handlerMap.get(type);
    }
}
