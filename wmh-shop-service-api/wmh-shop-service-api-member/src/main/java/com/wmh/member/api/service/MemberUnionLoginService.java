package com.wmh.member.api.service;

import com.alibaba.fastjson.JSONObject;
import com.wmh.common.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: create by wangmh
 * @name: MemberUnionLoginService.java
 * @description: 联合登录接口
 * @date:2020/3/22
 **/
@Api(tags = "联合登录接口")
public interface MemberUnionLoginService {

    @GetMapping("/unionLogin")
    @ApiOperation("根据不同的联合登录id")
    @ApiImplicitParam(name = "unionPublicId", paramType = "String", value = "wmh_qq")
    BaseResponse<String> unionLogin(@RequestParam("unionPublicId") String unionPublicId);

    @GetMapping("/login/oauth/callback")
    @ApiOperation("联合登录回调接口")
    @ApiImplicitParam(name = "unionPublicId", paramType = "String", value = "wmh_qq")
    BaseResponse<JSONObject> unionLoginCallback(@RequestParam("unionPublicId") String unionPublicId);

}
