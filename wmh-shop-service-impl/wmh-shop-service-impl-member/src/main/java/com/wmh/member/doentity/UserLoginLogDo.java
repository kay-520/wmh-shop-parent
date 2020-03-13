package com.wmh.member.doentity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: create by wangmh
 * @name: UserLoginLog.java
 * @description: 用户日志实体类
 * @date:2020/3/13
 **/
@Data
@TableName(value = "user_login_log")
public class UserLoginLogDo implements Serializable {
    private static final Integer DEFAULT_PID = 10;
    Long id;
    Long userId;
    String loginIp;
    Date loginTime;
    String loginToken;
    String channel;
    String equipment;

    public UserLoginLogDo(Long userId, String loginIp, Date loginTime, String loginToken, String channel, String equipment) {
        this.userId = userId;
        this.loginIp = loginIp;
        this.loginTime = loginTime;
        this.loginToken = loginToken;
        this.channel = channel;
        this.equipment = equipment;
    }
}
