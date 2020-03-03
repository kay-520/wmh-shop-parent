package com.wmh.member.fegin;

import com.wmh.wechat.api.service.WeChatService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: create by wangmh
 * @name: WeChatServiceFegin.java
 * @description: 调用微信服务
 * @date:2020/3/3
 **/
@FeignClient("wmh-weixin")
public interface WeChatServiceFegin extends WeChatService {
}
