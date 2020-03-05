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
//@EnableSwagger2Doc 若使用yml文件形式配置，注释掉swagger其他依赖，开启当前依赖，并在启动类上添加@EnableSwagger2Doc注解
public class WeChatApp {
    public static void main(String[] args) {
        SpringApplication.run(WeChatApp.class, args);
    }
}
