package com.wjg.boke.boke.config;

import com.wjg.boke.boke.im.handler.MsgHandlerFactory;
import com.wjg.boke.boke.im.server.ImServerStart;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@Slf4j
public class ImServeConfig {

    @Value("${im.server.port}")
    public int port;

   @Bean
   ImServerStart imServerStart(){
       try{
           //启动tio服务
           ImServerStart start=new ImServerStart(port);
           start.start();

           //初始化消息处理器类别
           MsgHandlerFactory.init();
           //返回tio服务
           return start;
       }catch (IOException e){
           log.error("tio server 启动失败", e);
       }
       //失败则返回null
       return null;
   }

}
