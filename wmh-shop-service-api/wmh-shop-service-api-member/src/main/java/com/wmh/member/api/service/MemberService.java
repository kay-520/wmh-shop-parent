package com.wmh.member.api.service;

import com.alibaba.fastjson.JSONObject;
import com.wmh.common.base.BaseResponse;
import com.wmh.member.api.dto.req.UserRegisterDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @author: create by wangmh
 * @name: MemberService.java
 * @description:会员服务接口
 * @date:2020/3/5
 **/
@Api(tags = "会员服务接口")
public interface MemberService {

    @PostMapping("register")
    @ApiOperation("会员注册接口")
    @ApiImplicitParam(name = "userRegisterDto", value = "{\"mobile\":\"xxxx\",\"password\":\"xxxx\"}", paramType = "body")
    @ApiResponse(code = 200, message = "register success！")
    BaseResponse<JSONObject> register(@Valid @RequestBody UserRegisterDto userRegisterDto, BindingResult bindingResult);

    @PostMapping("login")
    @ApiOperation("会员登录接口")
    @ApiImplicitParam(name = "userRegisterDto", value = "{\"mobile\":\"xxxx\",\"password\":\"xxxx\"}", paramType = "body")
    @ApiResponse(code = 200, message = "login success！")
    BaseResponse<JSONObject> login(@Valid @RequestBody UserRegisterDto userRegisterDto, BindingResult bindingResult, @RequestHeader("X-Real-IP")
            String sourceIp, @RequestHeader("channel") String channel, @RequestHeader("deviceInfor") String deviceInfor
    );


    @PostMapping("getUserInfo")
    @ApiOperation("根据token获取用户信息")
    @ApiImplicitParam(name = "token", value = "xxxxx", paramType = "String")
    BaseResponse<JSONObject> login(@RequestParam String token);
}
