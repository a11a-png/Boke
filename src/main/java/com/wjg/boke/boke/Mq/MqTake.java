package com.wjg.boke.boke.Mq;

import com.wjg.boke.boke.service.ISysarticles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

//队列接收回调
@Component
@RabbitListener(queues = "esQueue")
@Slf4j
public class MqTake {

    @Autowired
    public ISysarticles sysarticles;

    //监听队列confirmQueue
    @RabbitHandler
    public void takeEs(String msg) throws IOException {
       System.out.println("处理消息："+msg);
       sysarticles.UploadEs();
    }
}
