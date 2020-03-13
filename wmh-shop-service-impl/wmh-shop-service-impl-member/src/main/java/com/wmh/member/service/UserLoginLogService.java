package com.wmh.member.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmh.member.doentity.UserLoginLogDo;
import com.wmh.member.mapper.UserLoginLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
    @Autowired
    private UserLoginLogMapper userLoginLogMapper;
    @Async
    public void loginLog(UserLoginLogDo userLoginLogDo) {
        log.info(Thread.currentThread().getName() + ",userLoginLogDo:" + userLoginLogDo.toString() + ",流程2");
        userLoginLogMapper.insertUserLoginLog(userLoginLogDo);
    }

}
