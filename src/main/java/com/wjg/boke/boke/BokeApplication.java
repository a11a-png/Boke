package com.wjg.boke.boke;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//扫描dao层包
@MapperScan("com.wjg.boke.boke.dao")
//开启定时器
@EnableScheduling
public class BokeApplication {

    public static void main(String[] args) {

        SpringApplication.run(BokeApplication.class, args);
    }

}
