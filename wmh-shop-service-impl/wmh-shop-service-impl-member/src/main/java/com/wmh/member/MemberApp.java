package com.wmh.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: create by wangmh
 * @name: App.java
 * @description: 会员服务启动入口
 * @date:2020/3/3
 **/
@SpringBootApplication
@EnableFeignClients
@MapperScan("com.wmh.member.mapper")
@ComponentScan(basePackages={"com.wmh.member","com.wmh.common"})//启动扫包范围
public class MemberApp {
    public static void main(String[] args) {
        SpringApplication.run(MemberApp.class, args);
    }
}
