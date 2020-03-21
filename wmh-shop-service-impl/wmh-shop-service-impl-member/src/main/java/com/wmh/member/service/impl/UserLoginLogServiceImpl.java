package com.wmh.member.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmh.member.domain.UserLoginLogDo;
import com.wmh.member.mapper.UserLoginLogMapper;
import com.wmh.member.service.UserLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: create by wangmh
 * @name: UserLoginLogServiceImpl.java
 * @description: 用户登录日志service
 * @date:2020/3/13
 **/
@Service
@Slf4j
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLogDo> implements UserLoginLogService {

}
