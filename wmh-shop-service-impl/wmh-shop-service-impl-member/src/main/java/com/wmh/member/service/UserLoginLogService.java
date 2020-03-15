package com.wmh.member.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmh.member.doentity.UserLoginLogDo;
import com.wmh.member.mapper.UserLoginLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: create by wangmh
 * @name: UserLoginLogService.java
 * @description: 用户登录日志service
 * @date:2020/3/13
 **/
@Service
@Slf4j
public class UserLoginLogService extends ServiceImpl<UserLoginLogMapper, UserLoginLogDo> {

}
