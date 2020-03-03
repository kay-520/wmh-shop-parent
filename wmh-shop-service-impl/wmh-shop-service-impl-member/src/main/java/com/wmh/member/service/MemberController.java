package com.wmh.member.service;

import com.wmh.common.web.RespMsg;
import com.wmh.member.fegin.WeChatServiceFegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: create by wangmh
 * @name: MemberController.java
 * @description:
 * @date:2020/3/3
 **/
@RestController
public class MemberController {
    @Autowired
    private WeChatServiceFegin weChatServiceFegin;


    @GetMapping("appInfo")
    public String appInfo(@RequestParam("userId") Long userId){
        return weChatServiceFegin.getAppInfo(userId);
    }

    @GetMapping("test")
    public RespMsg<?> test(){
        return RespMsg.ok("hahahah");
    }
}
