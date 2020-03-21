package com.wmh.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wmh.job.domain.UserLoginLogDo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: create by wangmh
 * @name: UserLoginLogMapper.java
 * @description: 用户日志DAO
 * @date:2020/3/13
 **/
public interface UserLoginLogMapper extends BaseMapper<UserLoginLogDo> {
    @Select("select * from user_login_log limit #{index},#{pageSize}")
    List<UserLoginLogDo> selectByPage(Integer index, Integer pageSize);
}
