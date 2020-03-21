package com.wmh.member.api.service;

import com.alibaba.fastjson.JSONObject;
import com.wmh.common.base.BaseResponse;
import com.wmh.member.api.dto.req.UserMsgRegisterDto;
import com.wmh.member.api.dto.req.UserRegisterDto;
import com.wmh.member.api.dto.resp.UserRespDto;
import io.swagger.annotations.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("registerByMsg")
    @ApiOperation("会员短信注册接口")
    @ApiImplicitParam(name = "userMsgRegisterDto", value = "{\"mobile\":\"xxxx\",\"password\":\"xxxx\",\"code\":\"xxx\"}", paramType = "body")
    @ApiResponse(code = 200, message = "register success！")
    BaseResponse<JSONObject> registerByMsg(@Valid @RequestBody UserMsgRegisterDto userMsgRegisterDt, BindingResult bindingResult);

    @PostMapping("login")
    @ApiOperation("会员登录接口")
    @ApiImplicitParam(name = "userRegisterDto", value = "{\"mobile\":\"xxxx\",\"password\":\"xxxx\"}", paramType = "body")
    @ApiResponse(code = 200, message = "login success！")
    BaseResponse<JSONObject> login(@Valid @RequestBody UserRegisterDto userRegisterDto, BindingResult bindingResult, @RequestHeader("X-Real-IP")
            String sourceIp, @RequestHeader("channel") String channel, @RequestHeader("deviceInfor") String deviceInfor
    );

    @GetMapping("getUserInfo")
    @ApiOperation("根据token获取用户信息")
    @ApiImplicitParam(name = "token", value = "xxxxx", paramType = "String")
    BaseResponse<JSONObject> login(@RequestParam String token);


    @GetMapping("selectAndUpdateByOpenId")
    @ApiOperation("通过微信openid查询用户是否关注公众号,若未关注进行关联")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "xxxxx", paramType = "long"),
            @ApiImplicitParam(name = "openId", value = "xxxxx", paramType = "String")
    })
    BaseResponse<UserRespDto> selectAndUpdateByOpenId(@RequestParam long userId, @RequestParam String openId);

    @GetMapping("selectByOpenId")
    @ApiOperation("根据openId查询是否存在")
    @ApiImplicitParam(name = "openId", value = "xxxxx", paramType = "String")
    BaseResponse<JSONObject> selectByOpenId(@RequestParam String openId);

    @GetMapping("updateByOpenId")
    @ApiOperation("通过openId取消关联")
    @ApiImplicitParam(name = "openId", value = "xxxxx", paramType = "String")
    BaseResponse<JSONObject> updateByOpenId(@RequestParam String openId);

    @PostMapping("/sendMsg")
    @ApiOperation("发送短信")
    @ApiImplicitParam(name = "mobile", value = "13227088773", required = true)
    BaseResponse<JSONObject> sendMsg(@RequestParam("mobile") Long mobile);
}
