package com.wmh.member.strategy.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wmh.member.domain.UnionLoginDo;
import com.wmh.member.domain.UserDo;
import com.wmh.member.service.UserService;
import com.wmh.member.strategy.UnionLoginStrategy;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: create by wangmh
 * @name: WeChatUnionLoginStrategy.java
 * @description: 微信登录策略类
 * @date:2020/3/24
 **/
@Component("weChatUnionLoginStrategy")
public class WeChatUnionLoginStrategy implements UnionLoginStrategy {

    @Value("${wmh.login.wx.accesstoken}")
    private String weChatAccessTokenAddress;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Override
    public String unionLoginCallback(UnionLoginDo unionLoginDo, HttpServletRequest request) {
        String code = request.getParameter("code");
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        //1.替换生成accesstoken和openId的链接
        String accessTokenAddress = weChatAccessTokenAddress.replace("{APPID}", unionLoginDo.getAppId())
                .replace("{SECRET}", unionLoginDo.getAppKey()).replace("{CODE}", code);
        //1.code获取accesstoken和openId
        String accessToken = restTemplate.getForObject(accessTokenAddress, String.class);
        //{"access_token":"31_J29ufIUWbYf90yinw5iJXOYp4JkcjZzFw2wW7RBzqqFrxY0B5AfO7VU5Sr9589U-IbZMz_EQQz5qid9fHN9y7w",
        // "expires_in":7200,"refresh_token":"31_NgiO8ccyjOv7yzhmqL5MGFKpf-0yAys7LAb_4Q4tuWiy0mvqo-raA3QxrjBXi_SU4iTYAhRendbKGeWDfA4kQg",
        // "openid":"odRiAtxd0Piod7uKKOYGpxlIEYFI","scope":"snsapi_userinfo"}
        //判断是否存在错误
        if (accessToken.contains("errcode")) {
            return null;
        }
        JSONObject jsonObject = JSON.parseObject(accessToken);
        //获取openId
        String openid = jsonObject.getString("openid");
        //获取access_token
        String access_token = jsonObject.getString("access_token");
        if (StringUtils.isEmpty(openid)) {
            return null;
        }
        return openid;
    }

    /***
     * 根据wechatOpenId查询用户
     * @param openId
     * @return
     */
    @Override
    public UserDo selectOpenId(String openId) {
        QueryWrapper<UserDo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserDo::getWxOpenId, openId);
        return userService.getOne(wrapper);
    }
}
