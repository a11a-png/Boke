package com.wjg.boke.boke.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableAsync  //异步处理
@Configuration
@EnableWebSocketMessageBroker  // 表示开启使用STOMP协议来传输基于代理的消息
public class Wsconfig implements WebSocketMessageBrokerConfigurer  {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket") // 注册一个端点，websocket的访问地址
                .withSockJS(); //处理浏览器低级不支持情况
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/user/","/topic/");  //推送消息前缀
        registry.setApplicationDestinationPrefixes("/app");  //设置应用程序目标前缀
    }
}
