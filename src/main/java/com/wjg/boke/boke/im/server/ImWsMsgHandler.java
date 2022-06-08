package com.wjg.boke.boke.im.server;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.wjg.boke.boke.im.handler.MsgHandler;
import com.wjg.boke.boke.im.handler.MsgHandlerFactory;
import lombok.extern.slf4j.Slf4j;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.util.Map;

//tio插件 的各种回调处理方法
@Slf4j
public class ImWsMsgHandler implements IWsMsgHandler{
    /**
     * 握手时走的方法
     * @param httpRequest
     * @param httpResponse
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        //绑定个人通道
        String userID=httpRequest.getParam("userId");
        log.info("{} --------------> 正在握手！", userID);
        //给当前这个通道绑定用户id
        Tio.bindUser(channelContext,userID);
        //握手不能返回null 代表拒绝握手（拒接连接）
        return httpResponse;
    }

    /**
     * 握手完成之后
     * @param httpRequest
     * @param httpResponse
     * @param channelContext
     * @throws Exception
     */
    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        //绑定群聊
        Tio.bindGroup(channelContext,"e-group-study");
        log.info("{} --------------> 已绑定群！", channelContext.getId());
    }

    /**
     * 接收字节类型消息
     * @param wsRequest
     * @param bytes
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

    /**
     * 接收字符类型消息
     * @param wsRequest
     * @param text
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
        //处理消息
        if (text!=null && text.indexOf("ping")>0){
            log.info("接收到信息——————————————————>{}", text);
        }
        Map map= JSONUtil.toBean(text,Map.class);

        String type = MapUtil.getStr(map, "type");
        String data = MapUtil.getStr(map, "data");

        //根据类型 获取对应的处理器
        MsgHandler handler = MsgHandlerFactory.getMsgHandler(type);
        // 处理消息
        handler.handler(data, wsRequest, channelContext);

        return null;
    }

    /**
     * 链接关闭时候方法
     * @param wsRequest
     * @param bytes
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

}
