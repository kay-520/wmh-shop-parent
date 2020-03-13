package com.wmh.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wmh.member.doentity.UserLoginLogDo;
import org.apache.ibatis.annotations.Insert;

/**
 * @author: create by wangmh
 * @name: UserLoginLogMapper.java
 * @description: 用户日志DAO
 * @date:2020/3/13
 **/
public interface UserLoginLogMapper extends BaseMapper<UserLoginLogDo> {
    @Insert("insert into  user_login_log values(null,#{userId},#{loginIp},now(),#{loginToken},#{channel},#{equipment});")
    int insertUserLoginLog(UserLoginLogDo userLoginLogDo);
}
