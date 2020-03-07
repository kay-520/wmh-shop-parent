package com.wmh.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: create by wangmh
 * @name: App.java
 * @description: 会员服务启动入口
 * @date:2020/3/3
 **/
@SpringBootApplication
@EnableFeignClients
public class MemberApp {
    public static void main(String[] args) {
        SpringApplication.run(MemberApp.class, args);
    }
}
