package com.wmh.wechat.api.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: create by wangmh
 * @name: WeChatService.java
 * @description:
 * @date:2020/3/3
 **/
@Api(tags = "微信服务接口")
public interface WeChatService {
    /**
     * 微信应用信息
     *
     * @return
     */
    @GetMapping("/getAppInfo")
    @ApiOperation("getAppInfo接口")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true)
    @ApiResponse(code = 200, message = "响应成功")
    String getAppInfo(@RequestParam("userId") Long userId);

}
