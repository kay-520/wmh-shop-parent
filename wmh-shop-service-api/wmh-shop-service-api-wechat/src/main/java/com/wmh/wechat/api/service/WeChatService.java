package com.wmh.wechat.api.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: create by wangmh
 * @name: WeChatService.java
 * @description:
 * @date:2020/3/3
 **/
public interface WeChatService {
    /**
     * 微信应用信息
     *
     * @return
     */
    @GetMapping("/getAppInfo")
    String getAppInfo(@RequestParam("userId") Long userId);

}
