package com.wmh.member.strategy;

import com.wmh.member.domain.UnionLoginDo;
import com.wmh.member.domain.UserDo;

import javax.servlet.http.HttpServletRequest;

/***
 * 联合登录（策略模式）实现
 */
public interface UnionLoginStrategy {
    String unionLoginCallback(UnionLoginDo unionLoginDo, HttpServletRequest request);

    UserDo selectOpenId(String openId);
}
