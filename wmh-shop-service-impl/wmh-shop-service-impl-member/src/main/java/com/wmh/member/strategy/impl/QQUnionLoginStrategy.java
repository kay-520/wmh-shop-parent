package com.wmh.member.strategy.impl;

import com.wmh.common.util.TokenUtils;
import com.wmh.member.domain.UnionLoginDo;
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
@Component
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


    @Override
    public String unionLoginCallback(UnionLoginDo unionLoginDo, HttpServletRequest request) {
        String code = request.getParameter("code");
        //1.根据授权码accessToken
        qqAccessTokenAddres = qqAccessTokenAddres.replace("{client_id}"
                , unionLoginDo.getAppId()).replace("{client_secret}", unionLoginDo.getAppKey()).
                replace("{code}", code).replace("{redirect_uri}", unionLoginDo.getRedirectUri());
        log.info("qqAccessTokenAddres----------->{}", qqAccessTokenAddres);
        String accessToken = restTemplate.getForObject(qqAccessTokenAddres, String.class);
        log.info("accessToken----------->{}", accessToken);
        if (!accessToken.contains("access_token=")) {
            return null;
        }
        String[] split = accessToken.split("=");
        accessToken = split[1];
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
        String[] split1 = openId.replace("callback( {", "").
                replace("} );", "").split(",");
        openId = split1[1];
        //3.生成token
        String token = tokenUtils.createToken("qq.openid.", openId);
        return token;
    }
}