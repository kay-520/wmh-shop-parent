package com.wmh.job;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: create by wangmh
 * @name: AppJob.java
 * @description:
 * @date:2020/3/21
 **/
@SpringBootApplication
@MapperScan("com.wmh.job.mapper")
public class AppJob {
    public static void main(String[] args) {
        SpringApplication.run(AppJob.class, args);
    }
}
