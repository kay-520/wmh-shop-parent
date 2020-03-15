package com.wmh.member.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wmh.common.util.TokenUtils;
import com.wmh.member.doentity.UserLoginLogDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author: create by wangmh
 * @name: UserAsyncLogComponent.java
 * @description: 异步写入日志
 * @date:2020/3/15
 **/
@Component
@Slf4j
public class UserAsyncLogComponent {

    @Autowired
    private UserLoginLogService userLoginLogService;

    @Autowired
    private TokenUtils tokenUtils;

    @Async
    public void loginLog(UserLoginLogDo userLoginLogDo) {
        //1.查询日志表，用户是否登录 通过用户id 登录类型 可用状态查询
        QueryWrapper<UserLoginLogDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(UserLoginLogDo::getUserId, userLoginLogDo.getUserId())
                .eq(UserLoginLogDo::getIsAvailability, 1)
                .eq(UserLoginLogDo::getChannel, userLoginLogDo.getChannel());
        UserLoginLogDo userLoginLogDo1 = userLoginLogService.getOne(queryWrapper);
        //2.若用户已经登录，更新日志表状态，并删除redis中的token
        if (userLoginLogDo1 != null) {
            String oldLoginToken = userLoginLogDo1.getLoginToken();
            UpdateWrapper<UserLoginLogDo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().eq(UserLoginLogDo::getLoginToken, oldLoginToken);
            boolean flag = userLoginLogService.update(new UserLoginLogDo(0), updateWrapper);
            if (flag) {
                tokenUtils.delToken(oldLoginToken);
            }
        }
        log.info(Thread.currentThread().getName() + ",userLoginLogDo:" + userLoginLogDo.toString() + ",流程2");
        userLoginLogDo.setIsAvailability(1);
        userLoginLogService.save(userLoginLogDo);
    }
}
