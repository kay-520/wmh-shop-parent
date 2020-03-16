package com.wmh.wechat.controller;

import com.wmh.common.base.BaseApiService;
import com.wmh.wechat.api.service.WeChatService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: create by wangmh
 * @name: WeChatServiceImpl.java
 * @description: 微信服务实现接口
 * @date:2020/3/3
 **/
@RestController
public class WeChatController extends BaseApiService implements WeChatService {

    @Override
    public String getAppInfo(@RequestParam("userId") Long userId) {
        return "微信服务接口 userId:" + userId;
    }

}