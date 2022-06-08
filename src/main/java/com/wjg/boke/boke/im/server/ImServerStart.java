package com.wjg.boke.boke.im.server;

import org.tio.server.ServerGroupContext;
import org.tio.websocket.server.WsServerStarter;

import java.io.IOException;

//配置启动tio服务类
public class ImServerStart {

    private WsServerStarter start;

    public ImServerStart(int port) throws IOException {
        //tio插件的各种回调处理方法
        ImWsMsgHandler handler=new ImWsMsgHandler();
        start = new WsServerStarter(port,handler);

        ServerGroupContext serverGroupContext= start.getServerGroupContext();
        //设置心跳时间,若5秒内没有响应则断开连接
        serverGroupContext.setHeartbeatTimeout(5000);
    }

    public void start() throws IOException {
        start.start();
    }
}
