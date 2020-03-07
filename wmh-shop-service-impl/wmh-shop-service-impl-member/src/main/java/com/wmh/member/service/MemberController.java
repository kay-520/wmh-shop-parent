package com.wmh.member.service;

import com.wmh.member.api.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: create by wangmh
 * @name: MemberController.java
 * @description: 会员接口
 * @date:2020/3/3
 **/
@RestController
@RefreshScope //
public class MemberController implements MemberService {
    @Value("${wmh.name}")
    private String name;

    /***
     * 配置中心测试
     * @return
     */
    @GetMapping("get")
    public String get(){
        return name;
    }
}
