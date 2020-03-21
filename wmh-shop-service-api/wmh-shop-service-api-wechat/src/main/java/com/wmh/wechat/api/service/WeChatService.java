package com.wmh.wechat.api.service;

import com.alibaba.fastjson.JSONObject;
import com.wmh.common.base.BaseResponse;
import com.wmh.wechat.api.dto.LoginTemplateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @author: create by wangmh
 * @name: WeChatService.java
 * @description:
 * @date:2020/3/3
 **/
@Api(tags = "微信服务接口")
public interface WeChatService {

    @GetMapping("/getQrUrl")
    @ApiOperation("生成二维码接口")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true)
    BaseResponse<JSONObject> getQrUrl(@RequestParam("userId") Long userId);

    @PostMapping("/sendLoginTemplate")
    @ApiOperation("发送微信模板信息")
    @ApiImplicitParam(name = "loginTemplateDto", paramType = "body")
    BaseResponse<JSONObject> sendLoginTemplate(@Valid @RequestBody LoginTemplateDto loginTemplateDto, BindingResult bindingResult);


}
