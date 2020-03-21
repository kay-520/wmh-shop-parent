package com.wmh.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wmh.job.domain.UserLoginLogDo;

import java.util.List;

/**
 * @author: create by wangmh
 * @name: UserLoginLogService.java
 * @description:
 * @date:2020/3/21
 **/
public interface UserLoginLogService extends IService<UserLoginLogDo> {
    List<UserLoginLogDo> selectByPage(int index, Integer pageSize);
}
