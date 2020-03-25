package com.wmh.member.strategy.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wmh.common.util.TokenUtils;
import com.wmh.member.domain.UnionLoginDo;
import com.wmh.member.domain.UserDo;
import com.wmh.member.service.UserService;
import com.wmh.member.strategy.UnionLoginStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: create by wangmh
 * @name: UnionLoginStrategy.java
 * @description:
 * @date:2020/3/22
 **/
@Component("qQUnionLoginStrategy")
@Slf4j
public class QQUnionLoginStrategy implements UnionLoginStrategy {

    @Value("${wmh.login.qq.accesstoken}")
    private String qqAccessTokenAddres;

    @Value("${wmh.login.qq.openid}")
    private String qqOpenIdAddress;
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;


    @Override
    public String unionLoginCallback(UnionLoginDo unionLoginDo, HttpServletRequest request) {
        String code = request.getParameter("code");
        //1.根据授权码accessToken
        String accessTokenAddres = qqAccessTokenAddres.replace("{client_id}"
                , unionLoginDo.getAppId()).replace("{client_secret}", unionLoginDo.getAppKey()).
                replace("{code}", code).replace("{redirect_uri}", unionLoginDo.getRedirectUri());
        log.info("accessTokenAddres----------->{}", accessTokenAddres);
        String accessToken = restTemplate.getForObject(accessTokenAddres, String.class);
        log.info("accessToken----------->{}", accessToken);
        if (!accessToken.contains("access_token=")) {
            return null;
        }
        String[] split = accessToken.split("&");
        accessToken = split[0];//获取access_token=xxxxxx字符串
        if (StringUtils.isEmpty(accessToken)) {
            return null;
        }
        log.info("获取openId的地址：{}", qqOpenIdAddress + accessToken);
        //2.根据accessToken获取用户的openId
        String openId = restTemplate.getForObject(qqOpenIdAddress + accessToken, String.class);
        log.info("openId----------->{}", openId);
        if (StringUtils.isEmpty(openId)) {
            return null;
        }
        boolean flag = openId.contains("openid");
        if (!flag) {
            return null;
        }
        //callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} ); 处理字符串获取openId
        JSONObject jsonObject = JSONObject.parseObject(openId.replace("callback( ", "").
                replace(" );", ""));
        openId = (String) jsonObject.get("openid");
        log.info("openId----------->{}", openId);
        //3.生成token
        String token = tokenUtils.createToken("qq.openid.", openId);
        return token;
    }

    /***
     * 根据qqOpenId查询用户
     * @param openId
     * @return
     */
    @Override
    public UserDo selectOpenId(String openId) {
        QueryWrapper<UserDo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserDo::getQqOpenId, openId);
        return userService.getOne(wrapper);
    }
}
