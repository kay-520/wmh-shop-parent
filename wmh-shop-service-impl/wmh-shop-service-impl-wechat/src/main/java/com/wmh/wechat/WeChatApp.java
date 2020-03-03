package com.wmh.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: create by wangmh
 * @name: App.java
 * @description: 微信服务启动入口
 * @date:2020/3/3
 **/
@SpringBootApplication
public class WeChatApp {
    public static void main(String[] args) {
        SpringApplication.run(WeChatApp.class, args);
    }
}
