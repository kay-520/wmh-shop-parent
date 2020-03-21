package com.wmh.job.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmh.job.domain.UserLoginLogDo;
import com.wmh.job.mapper.UserLoginLogMapper;
import com.wmh.job.service.UserLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: create by wangmh
 * @name: UserLoginLogServiceImpl.java
 * @description:
 * @date:2020/3/21
 **/
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLogDo> implements UserLoginLogService {
    @Autowired
    private UserLoginLogMapper userLoginLogMapper;

    @Override
    public List<UserLoginLogDo> selectByPage(int index, Integer pageSize) {
        return userLoginLogMapper.selectByPage(index, pageSize);
    }
}
