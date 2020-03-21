package com.wmh.member.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("联合登录Do")
@TableName(value = "union_login")
public class UnionLoginDo {
    Long id;
    /**
     * 登陆名称 比如 腾讯QQ 腾讯支付
     */
    String unionName;
    /**
     * appId
     */
    String appId;
    /**
     * 联合登陆的id
     */
    String unionPublicId;
    /**
     * beanId
     */
    String unionBeanId;
    /**
     * appKey
     */
    String appKey;
    /**
     * redirectUri 回调地址
     */
    String redirectUri;
    /**
     * 回调地址
     */
    String requestAddress;

    /***
     * 是否可用
     */
    String isAvailability;
}
