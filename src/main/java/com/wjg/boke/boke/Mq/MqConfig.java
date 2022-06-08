package com.wjg.boke.boke.Mq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//队列
@Configuration
public class MqConfig {

    //创建队列
    @Bean
    public Queue esQueue(){
        return QueueBuilder.durable("esQueue").build();
    }

    //创建交换机
    @Bean
    public DirectExchange esexchange(){
        return new DirectExchange("esexchange");
    }

    //绑定队列与交换机
    @Bean
    public Binding bind(@Qualifier("esQueue") Queue esQueue,
                        @Qualifier("esexchange") DirectExchange esexchange){
        return BindingBuilder.bind(esQueue).to(esexchange).with("keyvalue");
    }
}
